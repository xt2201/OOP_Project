import socket
from threading import Thread
import json
import pandas as pd
from NewsAPI import NewsCaller
from SearchEngine import SearchEngine

import newsapi_top100 as api_caller

# Typing
Socket = socket.socket
DataFrame = pd.DataFrame


IP = "127.0.0.1"
PORT = 8888
BUFFER_SIZE = 2
ENCODING = "utf-8"
MAX_CONNECTION = 5


# Encoding and decoding message
def encode_message(msg: str) -> bytes:
    msg_length = len(msg).to_bytes(BUFFER_SIZE)
    bytes_msg = msg_length + msg.encode("utf-8")
    return bytes_msg


def decode_message(bytes_msg: bytes) -> str:
    msg = bytes_msg.decode(ENCODING)
    return msg


def send_message(client: Socket, msg: str) -> None:
    byte_msg = encode_message(msg)
    client.send(byte_msg)


def receive_message(client: Socket) -> str:
    msg_header = client.recv(BUFFER_SIZE)
    msg_length = int.from_bytes(msg_header)
    bytes_msg = client.recv(msg_length)
    msg = decode_message(bytes_msg)
    return msg


class SocketServer(Socket):
    def __init__(self) -> None:
        # Database
        self.database_path: str
        self.database: DataFrame
        # Search engine
        self.SE: SearchEngine
        self.caller = api_caller
        # Socket clients
        self.clients: list[Socket] = []
        self.nicknames: list[str] = []

    def start(self, ip: str, port: int, max_connection=5) -> None:
        super().__init__(socket.AF_INET, socket.SOCK_STREAM)
        self.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, True)
        self.bind((ip, port))
        self.listen(max_connection)
        print(f"Server started at {ip} on port {port}")
        self._receive()

    def set_database(self, path: str):
        self.database_path = path
        self.database = pd.read_csv(path)
        print(f"Database loaded from {path}")
        self.SE = SearchEngine(self.database)

    def search(self, query: str):
        results = self.SE.search(query, 10)
        search_items = [
            json.dumps(
                self.database.iloc[idx].fillna("null").to_dict(), allow_nan=False
            )
            for idx, _, _ in results
        ]
        print(self.database.iloc[2].fillna("null"))
        return search_items

    def call(self, query: str):
        caller = api_caller.NewsCaller(query, sort_by="publishedAt", page_size=10)
        called_items = [
            json.dumps(caller.get_single_article_details(idx))
            for idx in range(caller.page_size)
        ]
        return called_items

    def get_search_result(self, message: str):
        if message[0] == "1":
            return self.search(query=message[2:])
        if message[0] == "2":
            return self.call(query=message[2:])

    def _handle(self, client: Socket):
        while True:
            index = self.clients.index(client)
            nickname = self.nicknames[index]
            try:
                message = receive_message(client)
                client_input = message[message.find(":") + 1 :].strip()
                print(f"{nickname}: {client_input}")
                search_results = self.get_search_result(client_input)

                # Return search result
                send_message(client, f"-result {len(search_results)}")
                for i, item in enumerate(search_results):
                    send_message(client, f"-news {i} {item}")
                send_message(client, "-refresh")
            except Exception as e:
                print(e)
                self.clients.remove(client)
                self.nicknames.remove(nickname)
                client.close()
                break

    def _receive(self):
        while True:
            # Accepting a client connection
            client, address = self.accept()
            print(f"New connection from {address[0]}:{address[1]}")

            # Get client's nickname
            send_message(client, "-nick")
            nickname = receive_message(client)
            self.nicknames.append(nickname)
            self.clients.append(client)
            print(f"Nickname of client is {nickname}")

            # Start a seperate thread to handle the client
            thread = Thread(target=self._handle, args=(client,))
            thread.start()


if __name__ == "__main__":
    server = SocketServer()
    server.set_database("./Database/database.csv")
    server.start(IP, PORT)

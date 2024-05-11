import socket
import json
import pandas as pd
from threading import Thread
from SearchEngine_2 import SearchEngine

# Typing
Socket = socket.socket
DataFrame = pd.DataFrame


IP = "127.0.0.1"
PORT = 8888
BUFFER_SIZE = 2
ENCODING = "utf-8"
MAX_CONNECTION = 5


# Message handler
def encode_message(msg: str) -> bytes:
    msg_length = len(msg).to_bytes(BUFFER_SIZE)
    bytes_msg = msg_length + msg.encode("utf-8")
    return bytes_msg


def decode_message(bytes_msg: bytes):
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


class SocketServer:
    def __init__(self) -> None:
        # Database
        self.database_path: str
        self.database: DataFrame
        # Search engine
        self.SE: SearchEngine
        # Socket
        self.server: Socket | None = None
        self.clients: list[Socket] = []
        self.nicknames: list[str] = []

    def close_server(self) -> None:
        if self.server is not None:
            try:
                self.server.close()
            except Exception:
                print("Error while trying to close server")

    def start_server(self, ip: str, port: int, max_connection=5) -> None:
        self.close_server()
        server = Socket(socket.AF_INET, socket.SOCK_STREAM)
        server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, True)
        server.bind((ip, port))
        server.listen(max_connection)
        self.server = server
        print(f"Server started at {ip} on port {port}")
        self._receive()

    def set_database(self, path: str):
        self.database_path = path
        self.database = pd.read_csv(path)
        print(f"Database loaded from {path}")
        self.SE = SearchEngine(self.database)

    def get_search_result(self, query: str):
        results = self.SE.search(query)
        search_items = [
            json.dumps(self.database.iloc[idx].to_dict()) for idx, _, _ in results
        ]
        return search_items

    def _handle(self, client: Socket):
        while True:
            index = self.clients.index(client)
            nickname = self.nicknames[index]
            try:
                message = receive_message(client)
                client_input = message[message.find(":") + 1 :].strip()
                print(f"{nickname}: {client_input}")
                search_results = self.get_search_result(client_input)
                send_message(client, "Searching Done")
                for item in search_results:
                    print(item)
                    send_message(client, item)
                    break
            except Exception as e:
                print(e)
                self.clients.remove(client)
                client.close()
                self.nicknames.remove(nickname)
                break

    def _receive(self):
        while True:
            # Accepting a client connection
            client, address = self.server.accept()
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
    server.start_server(IP, PORT)

from googlesearch import search


def main():
    with open("./WebCrawler/blockchain_url.txt", "a") as file:
        for url in search("blockchains", start=101, stop=200, pause=10):
            print(url)
            file.write(url + "\n")
    print("Scrapping done")


if __name__ == "__main__":
    # main()
    pass

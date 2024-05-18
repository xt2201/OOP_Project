from newsapi import NewsApiClient


class NewsCaller(NewsApiClient):
    def __init__(self, query, sort_by, page_size):
        super().__init__(api_key="9264da2e99d64499b3175a5029bd6d9a")
        self.query = query
        self.sort_by = sort_by
        self.language = "en"
        self.page_size = page_size
        self.articles = self.get_everything(
            q=self.query,
            language=self.language,
            page_size=page_size,
            sort_by=self.sort_by,
        )["articles"]

    # Function to get article type based on source name
    def get_article_type(self, source_name):
        if "twitter" in source_name.lower():
            return "Tweet"
        elif "facebook" in source_name.lower():
            return "Facebook Post"
        else:
            return "News Article"

    def get_single_article_details(self, article_id):
        print(article_id)
        return {
            "id": article_id,
            "link": self.articles[article_id]["url"],
            "source": self.articles[article_id]["source"]["name"],
            "type": self.get_article_type(
                str(self.articles[article_id]["source"]["name"])
            ),
            "title": self.articles[article_id]["title"],
            "summary": (
                self.articles[article_id]["description"]
                if self.articles[article_id]["description"]
                else "N/A"
            ),
            "content": (
                self.articles[article_id]["content"]
                if self.articles[article_id]["content"]
                else "N/A"
            ),
            "publishDate": self.articles[article_id]["publishedAt"],
            "tags": "N/A",  # Tags/Hashtags are not available in News API
            "author": (
                self.articles[article_id]["author"]
                if self.articles[article_id]["author"]
                else "N/A"
            ),
            "category": self.articles[article_id].get(
                "category", "N/A"
            ),  # Use .get() method to handle KeyError
            "image": (
                self.articles[article_id]["urlToImage"]
                if self.articles[article_id]["urlToImage"]
                else "N/A"
            ),
        }

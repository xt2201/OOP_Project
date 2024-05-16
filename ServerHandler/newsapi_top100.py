from newsapi import NewsApiClient
import csv
import os


class NewsCaller:
    def __init__(self, query, sort_by, page_size):
        self.newsapi = NewsApiClient(api_key='9264da2e99d64499b3175a5029bd6d9a')
        self.query = query
        self.sort_by = sort_by
        self.language = 'en'
        self.page_size = page_size
        self.articles = self.newsapi.get_everything(q = self.query,
                                        language = self.language,
                                        page_size = page_size,
                                        sort_by= self.sort_by)['articles']
        

# Function to get article type based on source name
    def get_article_type(self, source_name):
        if 'twitter' in source_name.lower():
            return 'Tweet'
        elif 'facebook' in source_name.lower():
            return 'Facebook Post'
        else:
            return 'News Article'
        

    def get_single_article_details(self, article_id):
        print(article_id)
        return {
            "id": article_id,
            "link": self.articles[article_id]['url'],
            "source": self.articles[article_id]['source']['name'],
            "type": self.get_article_type(str(self.articles[article_id]['source']['name'])),
            "title": self.articles[article_id]['title'],
            "summary": self.articles[article_id]['description'] if self.articles[article_id]['description'] else "N/A",
            "content": self.articles[article_id]['content'] if self.articles[article_id]['content'] else "N/A",
            "publishDate": self.articles[article_id]['publishedAt'],
            "tags": "N/A",  # Tags/Hashtags are not available in News API
            "author": self.articles[article_id]['author'] if self.articles[article_id]['author'] else "N/A",
            "category": self.articles[article_id].get('category', "N/A"),  # Use .get() method to handle KeyError
            "image": self.articles[article_id]['urlToImage'] if self.articles[article_id]['urlToImage'] else "N/A"
        }


# caller = NewsCaller('dogecoin', 'publishedAt', 10)
# print(caller.get_single_article_details(1))

# class Article:
#     def __intit__(self, id , link, source, type, title, summary, content, publishDate, tags, author, category, image):
#         self.id = id
#         self.link = link
#         self.source = source
#         self.type = type
#         self.title = title
#         self.summary = summary
#         self.content = content
#         self.publishDate = publishDate
#         self.tags = tags
#         self.author = author
#         self.category = category
#         self.image = image
# # Function to gather article details with an ID into a dictionary
#     def get_article_details(self):
#         return {
#             "id": self.id,
#             "link": self.link,
#             "source": self.source,
#             "type": self.type,
#             "title": self.title,
#             "summary": self.summary,
#             "content": self.content,
#             "publishDate": self.publishDate,
#             "tags": self.tags,
#             "author": self.author,
#             "category": self.category,
#             "image": self.image
#         }
    
    # def get_article_details(article, article_id):
    #     return {
    #         "id": article_id,
    #         "link": article['url'],
    #         "source": article['source']['name'],
    #         "type": get_article_type(article['source']['name']),
    #         "title": article['title'],
    #         "summary": article['description'] if article['description'] else "N/A",
    #         "content": article['content'] if article['content'] else "N/A",
    #         "publishDate": article['publishedAt'],
    #         "tags": "N/A",  # Tags/Hashtags are not available in News API
    #         "author": article['author'] if article['author'] else "N/A",
    #         "category": article.get('category', "N/A"),  # Use .get() method to handle KeyError
    #         "image": article['urlToImage'] if article['urlToImage'] else "N/A"
    #     }
    


# Main function
    # def call(self):
    #     file_name = f'top{self.page_size}_f{self.query}.csv'
    #     file_exists = os.path.isfile(file_name)

    #     # Open the CSV file in append mode
    #     with open('top100_blockchain.csv', mode='w', newline='') as file:
    #         writer = csv.DictWriter(file, fieldnames=[
    #             "id", "link", "source", "type", "title", "summary", "content", "publishDate", "tags", "author", "category", "image"
    #         ])

    #         # Write the header only if the file does not exist
    #         if not file_exists:
    #             writer.writeheader()

    #         # Get the current max ID in the file if it exists
    #         # current_max_id = 0
    #         # if file_exists:
    #         #     with open('articles.csv', mode='r', newline='') as read_file:
    #         #         reader = csv.DictReader(read_file)
    #         #         for row in reader:
    #         #             current_max_id = max(current_max_id, int(row['id']))

    #         # Write the articles to the CSV file
    #         for i in range (self.page_size):
    #             try:
    #                 article_details = self.get_single_article_details(i)
    #             except:
    #                 print(self.articles[i]['source']['name'][1])
    #                 print(type(self.articles[i]['source']['name']))

    #             article_details = self.get_single_article_details(i)
    #             writer.writerow(article_details)

    #     print("CSV file 'articles.csv' updated successfully.")

        





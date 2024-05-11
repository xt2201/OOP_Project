import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

DataFrame = pd.DataFrame


class SearchEngine:

    def __init__(self, database: DataFrame):
        self.data = database
        self.model = SentenceTransformer("paraphrase-MiniLM-L6-v2")
        self.tfidf_vectorizer = TfidfVectorizer()
        self.tfidf_matrix = self._calculate_tfidf_matrix()

    def _calculate_tfidf_matrix(self):
        tfidf_matrix = self.tfidf_vectorizer.fit_transform(self.data["content"].values)
        return tfidf_matrix

    def search(self, query, top_k=5):
        query_embedding = self.model.encode([query])[0]
        query_tfidf = self.tfidf_vectorizer.transform([query])
        tfidf_similarity = cosine_similarity(query_tfidf, self.tfidf_matrix)[0]
        semantic_similarity = cosine_similarity(
            [query_embedding], self.model.encode(self.data["content"].values)
        )[0]
        combined_similarity = 0.5 * tfidf_similarity + 0.5 * semantic_similarity
        indices = np.argsort(combined_similarity)[-top_k:][::-1]
        results = [
            (
                self.data.iloc[idx]["id"],
                self.data.iloc[idx][["content", "publishDate"]],
                combined_similarity[idx],
            )
            for idx in indices
        ]
        return results


if __name__ == "__main__":
    # Load data from CSV file
    data_path = "./Database/database.csv"
    database = pd.read_csv("./Database/database.csv")

    # Create search engine instance
    search_engine = SearchEngine(database)

    # Perform search
    query = "New York crypto news"
    results = search_engine.search(query)
    for idx, text, similarity in results:
        # print(f"ID: {idx}, Similarity: {similarity:.4f}, Text: {text}")
        print(database.iloc[idx].to_dict())

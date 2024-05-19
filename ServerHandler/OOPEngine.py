class SearchEngine:
    def __init__(self, retrievePipeline):
        self.__retrievePipeline = retrievePipeline

    def execute_pipeline(self):
        self.__retrievePipeline.execute()
        
    def search(self, query):
        result = self.__retrievePipeline.run(
        {"text_embedder": {"text": query}, "bm25_retriever": {"query": query}, "ranker": {"query": query}})
        
        return [doc.meta["title"] for doc in result["ranker"]["documents"]]
    
    @staticmethod
    def filter(lst: list) -> list:
        out = []
        for i in lst:
            if i not in out:
                out.append(i)
            else: continue
        return out    
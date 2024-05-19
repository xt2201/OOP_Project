from BlockchainDB import *
from OOPEngine import SearchEngine
from IndexPipeline import IndexPipeline
from RetrieverPipeline import RetrieverPipeline
from haystack.document_stores.in_memory import InMemoryDocumentStore

my_db = ExcelDB("Database/database.csv")
# Stating the number of considered articles 
my_db.process_data(20)



index = IndexPipeline(
    docEmbedderModel = "sentence-transformers/all-MiniLM-L6-v2", 
    docs = my_db.getAllArticles(), 
    documentStore = InMemoryDocumentStore()
    )

index.execute()
index.draw("indexing_pipeline.png")

retrieve = RetrieverPipeline(
    textEmbedderModel = "sentence-transformers/all-MiniLM-L6-v2", 
    rankModel = "BAAI/bge-reranker-base", 
    embeddedDocumentStore = index.getDocumentStore()
    )

retrieve.draw("retrieval_pipeline.png")

query = "America"

engine = SearchEngine(retrievePipeline= retrieve)
engine.execute_pipeline()
result = my_db.getIndex(engine.filter(engine.search(query)), 10)
print(result)
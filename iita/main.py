from fastapi import FastAPI
from typing import List
from pydantic import BaseModel
import pandas as pd
from learning_spaces.kst import iita

class Data(BaseModel):
  name: str
  values: List[int]

class DataFrame(BaseModel):
  data: List[Data]

app = FastAPI()

def to_dict(df: DataFrame):
  my_dict = {}
  for t in df.data:
    my_dict[t.name] = t.values
  return my_dict

@app.post("/ita")
def submit(df: DataFrame):
  data_frame = pd.DataFrame(to_dict(df))
  response = iita(data_frame, v=1)
  response['diff']=response['diff'].tolist()
  return {
    "data": response
  }
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
def submit(df: dict):
  data_frame = pd.DataFrame(df['results'])
  response = iita(data_frame, v=1)
  response['diff']=response['diff'].tolist()
  implications = response['implications']
  no_dups_implications = set()
  for impl in implications:
    if not (impl in no_dups_implications or \
      (impl[1], impl[0]) in no_dups_implications):
      no_dups_implications.add(impl)
  return list(no_dups_implications)
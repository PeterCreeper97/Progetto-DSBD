from operator import index
import matplotlib.pyplot as plt 
import numpy as np
import pandas as pd
from statsmodels.tsa.seasonal import seasonal_decompose
from statsmodels.tsa.holtwinters import ExponentialSmoothing


df = pd.read_csv("./menu.csv", sep=",", header=None, parse_dates=[0], index_col=[0])
tsr = df.resample(rule="0.25T").mean()

tsr_rol = tsr.rolling(window=5).mean()
tsr_rol = tsr_rol.shift(-5)
tsr_rol = tsr_rol[:-5]
train_data = tsr_rol[:97]
train_data2 = tsr_rol[:98]
test_data = tsr_rol[97:121]

#Resid, Seasonal, Trend, ...
tsr_sea_dec = seasonal_decompose(train_data, model='add', period=23)
tsr_sea_dec.plot()


#Test prediction
tsmodel = ExponentialSmoothing(train_data, trend='mul', seasonal='mul', seasonal_periods=23).fit()
prediction = tsmodel.forecast(3)
plt.figure(figsize=(24,10), dpi=100)
plt.ylabel('Values',fontsize=14)
plt.xlabel('Time',fontsize=14)
plt.plot(train_data2,"-", label="Train Data", color="blue")
plt.plot(test_data,"--", label="Test Data", color="green")
plt.plot(prediction,"-", label="Prediction", color="red")
plt.title("Test Prediction")
plt.legend(title='Legend', fontsize=12)

#Future prediction
tsmodel = ExponentialSmoothing(tsr_rol, trend='mul', seasonal='mul', seasonal_periods=23).fit()
prediction = tsmodel.forecast(3)
plt.figure(figsize=(24,10), dpi=100)
plt.ylabel('Values',fontsize=14)
plt.xlabel('Time',fontsize=14)
plt.plot(tsr_rol,"-", label="Train Data", color="blue")
plt.plot(prediction,"-", label="Prediction", color="red")
plt.title("Future Prediction")
plt.legend(title='Legend', fontsize=12)
plt.show()
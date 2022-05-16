package com.mycompany.app;

import java.util.ArrayList;
import java.util.logging.Logger;
interface Observer{
    public void update(DisplayDevice d);


}

interface Subject{

public void addObserver(Observer observer);
public void removeObserver(Observer observer);
public void notifyObservers();

}

class WeatherStation implements Subject {
     
    private double temperature;
    private double pressure;
    private double humidity;
    private ArrayList<Observer> observers =new ArrayList<Observer>();
    private DisplayDevice d = new DisplayDevice();
    public double getTemperature(){
        return temperature;
    }
    public double getPressure(){
        return pressure;
    }
    public double getHumidity(){
        return humidity;
    }

    public void setParameters(double temperature, double pressure, double humidity){
        this.temperature =temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        notifyObservers();
    }
    public void setDevice(DisplayDevice d){
        this.d = d;
    }

    @Override
    public void addObserver(Observer o){
       observers.add(o);
    }

    @Override
    public void removeObserver(Observer o){
       observers.remove(o);
    }
    
    @Override
    public void notifyObservers(){
        d.setParameters(this.temperature,this.pressure,this.humidity);
        for(Observer o: observers){
            o.update(d);
        }

    }


}


class Current implements Observer{

    @Override
    public void update(DisplayDevice d){
        d.current();
    }
}

class Statistics implements Observer{

    @Override
    public void update(DisplayDevice d){
         d.mean();
    }
}

class Forecast implements Observer{

    @Override
    public void update(DisplayDevice d){
        d.prediction();
    }
}

class DisplayDevice{
    private ArrayList<Double> listTemperature = new ArrayList<Double>();
    private ArrayList<Double> listPressure = new ArrayList<Double>();
    private ArrayList<Double> listHumidity= new ArrayList<Double>();;
    Logger logger = Logger.getLogger(DisplayDevice.class.getName());
    

    public void setParameters(double temperature, double pressure, double humidity){
        listTemperature.add(temperature);
        listPressure.add(pressure);
        listHumidity.add(humidity);
    }

    public void current(){
       String temp= "The current temperature is: " + listTemperature.get(listTemperature.size()-1);
       String press= "The current pressure is: " + listPressure.get(listPressure.size()-1);
       String hum= "The current humidity is: " + listHumidity.get(listHumidity.size()-1);
       logger.info(temp);
       logger.info(press);
       logger.info(hum);
    }

    public void mean(){
        double sumt=0;
        double pre=0;
        double hum=0;
        for(double temp: listTemperature){
          sumt+=temp;
        }
        for(double pres: listPressure){
            pre+=pres;
        }
        for(double humi: listHumidity){
            hum+=humi;
        }
        String temp="the mean of temperature is: " + sumt/listTemperature.size();
        String pres="the mean of pressure is: " + pre/listTemperature.size();
        String humi="the mean of humidity is: " + hum/listTemperature.size();
        logger.info(temp);
        logger.info(pres);
        logger.info(humi);
    }
    public void prediction(){
        logger.info("eae pillin");
    }
    public void show(){
       current();
       mean();
       prediction();
       

        
    }


}

class Main{

    public static void main(String[] args){
        WeatherStation w = new WeatherStation();
        Forecast f = new Forecast();
        Current c = new Current();
        Statistics s = new Statistics();
        DisplayDevice d = new DisplayDevice();
        w.setDevice(d);
        w.addObserver(f);
        w.addObserver(c);
        w.addObserver(s);
        w.setParameters(20, 30, 40);
        w.setParameters(50, 60, 50);
        d.show();
    }
}
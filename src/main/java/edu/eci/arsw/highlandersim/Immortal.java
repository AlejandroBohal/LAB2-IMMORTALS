package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback = null;
    private AtomicInteger health;
    private int defaultDamageValue;
    private final CopyOnWriteArrayList<Immortal> immortalsPopulation;
    private final String name;
    private final Random r = new Random(System.currentTimeMillis());
    private boolean running;
    private long id;
    private AtomicBoolean alive = new AtomicBoolean(true);

    public Immortal(String name,long id, CopyOnWriteArrayList<Immortal> immortalsPopulation, AtomicInteger health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback = ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue = defaultDamageValue;
        this.running = true;
        this.id = id;
    }

    public void run() {

        while (alive.get()) {
            while (!running) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Immortal im;
            int myIndex = immortalsPopulation.indexOf(this);
            int nextFighterIndex = r.nextInt(immortalsPopulation.size());
            //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }
            im = immortalsPopulation.get(nextFighterIndex);
            if (getHealth().get()>0){
                this.fightSync(im);
            }else{
                this.kill();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void fightSync(Immortal i2){
        long thisId = this.getId();
        long i2Id = i2.getId();

        if(thisId < i2Id){
            synchronized (this){
                synchronized (i2){
                    this.fight(i2);
                }
            }
        } else{
            synchronized (i2){
                synchronized (this){
                    this.fight(i2);
                }
            }
        }
    }
    public synchronized void fight(Immortal i2) {

        if (i2.getHealth().get() > 0 && this.getHealth().get()>0) {
            i2.getHealth().getAndAdd(-defaultDamageValue);
            health.getAndAdd(defaultDamageValue);
            if (updateCallback != null){
                updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
            }
        } else {
            if (updateCallback != null) {
                updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
            }
        }

    }

    public synchronized AtomicInteger getHealth() {
        return health;
    }
    @Override
    public String toString() {

        return name + "[" + health + "]";
    }


    public void setRunning(boolean running) {
        if (running) {
            synchronized (this) {
                notify();
            }
        }
        this.running = running;
    }
    public void kill(){
        immortalsPopulation.remove(this);
        this.alive.getAndSet(false);
    }
    public void setAlive(boolean alive){
        this.alive.getAndSet(alive);
    }
    @Override
    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

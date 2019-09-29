package com.company;
/*
    Doplňte funkcionalitu pre aplikáciu Reakčná doba

    Princíp hry

    Hráč po zadaní svojho mena spustí hru.
    Na otázku "Pripraveny ?" odpovedá stlačením ENTER
    Objaví sa hlásenie "Pozooor ..."
    a po náhodne dlhej dobe v intervale 0.5 - 3 sekundy sa objaví povel "START !!!"
    Cieľom hráča je v najrýchlejšom možnom čase opäť stlačiť ENTER.
    Program vypíše čas v milisekundách, ktorý uplynul od zobrazenia povelu START po stlačenie ENTER
    a zaradí ho do usporiadanej tabuľky výkonov (Meno hráča + výkon)
    Na obrazovku vypíše, kde sa daný výkon v tabuľke nachádza a to tak, že vypíše
    5 bezprostredne predchádzajúcich výkonov
    aktuálny výkon
    5 bezprostredne nasledujúcich výkonov
    To všetko v tvare Poradové číslo v tabuľke výkonov Tab6 Meno hráča Tab25 výkon
    Celú tabuľku s novým záznamom zapíše do textového súboru na disk, každý riadok v tvare MenoHraca:vykon

    Hra po spustení načíta zo súboru aktuálnu tabuľku výkonov a požiada hráča o prihlásenie (zadanie mena)
    Potom zobrazí MENU s položkami
    1 - Spusť hru
    2 - Zmena hráča
    3 - TOP 10
    4 - Koniec
    A reaguje podľa výberu

    Hru naprogramujte ako konzolovú aplikáciu aj ako aplikáciu s GUI. Využite pritom MVC.
    Pre meranie času využite funkciu System.currentTimeMillis();
    Hra musí ošetriť aj predčasné stlačenie pred zobrazením START ako chybu a potrestať ju (spôsob trestu je na vás)

    -----------------------------
    load the results
    log in (name)
    show the menu -> play
    ready, set, go -> press enter
    count time
    sort, show, save the results
    -----------------------------
*/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class ReactBase {
    final int CM_PLAY = 1;
    final int CM_CHANGE_PLAYER = 2;
    final int CM_TOP10 = 3;
    final int CM_QUIT = 4;
    String Player;

    Scanner sc = new Scanner(System.in);
    Random rn = new Random();
    BufferedWriter bf;

    ArrayList<Record> tabulka = new ArrayList();
    Sort sorter = new Sort();

    public static void main(String[] args) {
        boolean gameOn;
	    ReactBase Game = new ReactBase();
	    do
            gameOn =  Game.Run();
        while (gameOn);
    }

    private ReactBase() {
        ImportRecords();
        NewPlayer();
    }

    private boolean Run(){
        switch(Menu()){
            case CM_CHANGE_PLAYER:
                NewPlayer();
                return true;
            case CM_PLAY:
                long LastTime = Play(Player);
                Record rec = Sort(Player, LastTime);
                ShowRecords(rec);
                SaveRecords();
                return true;
            case CM_TOP10:
                ShowRecords(null);
                return true;
            case CM_QUIT:
                return false;
        }
        return true;
    }

    /////////////////////////////////
    /////////////////////////////////

    private void ImportRecords() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("records"));
            String line = br.readLine();

            while (line != null){
                String[] data = line.split(":");
                tabulka.add(new Record(data[0], Integer.parseInt(data[1])));
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////

    public void NewPlayer(){
        System.out.println("Your name: ");
        Player = sc.nextLine();
    }

    public int Menu(){
        System.out.println("1 - Start game\n" +
                "2 - Change player\n" +
                "3 - TOP 10\n" +
                "4 - End");
        try {
            int vstup = Integer.parseInt(sc.nextLine());

            if (vstup >= 1 && vstup <= 4)
                return vstup;
        } catch (Exception e) {
            return CM_QUIT;
        }
        return CM_QUIT;
    }

    /////////////////////////////////////////////

    private long Play(String who) {
        System.out.println("Ready?");
        sc.nextLine();
        System.out.println("Set ...");
        try {
            TimeUnit.SECONDS.sleep((long) (0.5 + rn.nextDouble() * (3 - 0.5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("GO!");
        long startTime = System.currentTimeMillis();
        sc.nextLine();
        long endTime = System.currentTimeMillis();

        long time = endTime - startTime;
        if (time == 0) {
            System.out.println("Try again...");
            return 1000000;
        }

        return time;
    }

    private Record Sort(String who, long record){
        Record rec = new Record(who, record);
        tabulka.add(rec);
        tabulka.sort(sorter);
        return rec;
    }

    private void ShowRecords(Record record){
        if (record == null) {
            for (int i = 0; i < tabulka.size(); i++) {
                Record rec = tabulka.get(i);
                String indStr = (i + 1) + ".";
                System.out.printf("%-6s %-25s %d\n", indStr, rec.name, rec.record);
                if (i == 9) break;
            }
        } else {
            int index = tabulka.indexOf(record);
            for (int i = 0; i < tabulka.size(); i++) {
                if (i <= index + 5 && i >= index - 5) {
                    Record rec = tabulka.get(i);
                    String indStr = (i + 1) + ".";
                    System.out.printf("%-6s %-25s %d\n", indStr, rec.name, rec.record);
                }
            }
        }
    }

    private void SaveRecords(){
        try {
            bf = new BufferedWriter(new FileWriter("records"));
            for (Record rec: tabulka) {
                bf.write(rec.name + ":" + rec.record + "\n");
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

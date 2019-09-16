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

public class ReactBase {
    final int CM_PLAY = 1;
    final int CM_CHANGE_PLAYER = 2;
    final int CM_TOP10 = 3;
    final int CM_QUIT = 4;
    int gameSelection;
    long end;
    long begin;
    String Player;
    String time;
    HashMap<String, String> records = new HashMap<String, String>();
    Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws IOException {
        boolean gameOn;
	    ReactBase Game = new ReactBase();
	    do
            gameOn =  Game.Run();
        while (gameOn);
    }

    private ReactBase() throws IOException {
        ImportRecords();
        System.out.println("Zadaj meno hraca: ");
        NewPlayer();
    }

    private boolean Run(){
        switch(Menu()){
            case CM_CHANGE_PLAYER:
                NewPlayer();
                return true;
            case CM_PLAY:
                int LastTime = Play(Player);
                Sort(Player, LastTime);
                ShowRecords(Player, LastTime);
                SaveRecords();
                return true;
            case CM_TOP10:
                ShowRecords("", 0);
                return true;
            case CM_QUIT:
                return false;
        }
        return true;
    }

    private void ImportRecords() throws IOException {
        BufferedReader file = new BufferedReader(new FileReader("records.txt"));
        String [] rec = file.readLine().split(";");
        String [] line;
        for (int i=0; i<rec.length; i++) {
            line = rec[i].split(" ");
            records.put(line[0], line[1]);
        }
        /*for (String j : records.keySet()) {
            //System.out.println(j + " " + records.get(j));
        }*/
    }

    private void NewPlayer(){
        Player = sc.nextLine();
        System.out.println("1 - Spusť hru\n" + "2 - Zmena hráča\n" + "3 - TOP 10\n" + "4 - Koniec");
    }

    private int Menu(){
        gameSelection = sc.nextInt();
        return gameSelection;//CM_QUIT;
    }

    private int Play(String who){
        begin = System.currentTimeMillis();
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                end = System.currentTimeMillis();
            }
        };
        JTextField textField = new JTextField(10);
        textField.addActionListener( action );
        //time = (end-begin);
        records.put(who, time);
        return Integer.MAX_VALUE;
    }

    private void Sort(String who, int record){}

    private void ShowRecords(String who, int record){}

    private void SaveRecords(){}

}

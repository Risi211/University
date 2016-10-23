/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author lupin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //list of participants
        IColleague<String> colleagueA = new Colleague<>("ColleagueA");
        IColleague<String> colleagueB = new Colleague<>("ColleagueB");
        IColleague<String> colleagueC = new Colleague<>("ColleagueC");
        IColleague<String> colleagueD = new Colleague<>("ColleagueD");

        //first mediator
        IMediator<String> mediator1 = new Mediator<>();
        //participants registers to the mediator
        mediator1.registerColleague(colleagueA);
        mediator1.registerColleague(colleagueB);
        mediator1.registerColleague(colleagueC);
        //participantA sends out a message
        colleagueA.send(mediator1, "MessageX from ColleagueA");

        //second mediator
        IMediator<String> mediator2 = new Mediator<>();
        //participants registers to the mediator
        mediator2.registerColleague(colleagueB);
        mediator2.registerColleague(colleagueD);
        //participantB sends out a message
        colleagueB.send(mediator2, "MessageY from ColleagueB");        
    }
    
}

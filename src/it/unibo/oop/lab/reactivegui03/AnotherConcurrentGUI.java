package it.unibo.oop.lab.reactivegui03;

import it.unibo.oop.lab.reactivegui02.ConcurrentGUI;
import it.unibo.oop.lab.reactivegui02.ConcurrentGUI.Agent;

/*
 * Realizzare una classe AnotherConcurrentGUI con costruttore privo di argomenti, 
 * che aggiunga all'esercizio precedente la seguente funzionalità:
 * - dopo 10 secondi dalla partenza dell'applicazione, i pulsanti si disabilitino 
 *   e il conteggio si fermi comunque.
 *   - fare attenzione a non creare corse critiche
 * 
 * Suggerimenti: 
 * - si usi un ulteriore agente... 
 * - si rifattorizzi se necessario/utile la struttura della soluzione
 */
public class AnotherConcurrentGUI extends ConcurrentGUI {
    public AnotherConcurrentGUI() {
        super();
        AnotherConcurrentGUIAgent agent2 = new AnotherConcurrentGUIAgent();
        final ConcurrentGUI.ThreadPerTaskExecutor executor = new ConcurrentGUI.ThreadPerTaskExecutor();
        executor.execute(new Agent());
        executor.execute(agent2);
    }
    public class AnotherConcurrentGUIAgent extends Agent { 
        @Override
        public void run() {
            try {
                Thread.sleep(100);
                up.setEnabled(false);
                down.setEnabled(false);
                stopBtn.setEnabled(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
            }
        }



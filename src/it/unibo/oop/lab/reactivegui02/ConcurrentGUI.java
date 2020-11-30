package it.unibo.oop.lab.reactivegui02;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;







public class ConcurrentGUI extends JFrame{
    /*
     * Realizzare una classe ConcurrentGUI con costruttore privo di argomenti,
     * tale che quando istanziata crei un JFrame con l'aspetto mostrato nella
     * figura allegata (e contatore inizialmente posto a zero).
     * 
     * Il contatore venga aggiornato incrementandolo ogni 100 millisecondi
     * circa, e il suo nuovo valore venga mostrato ogni volta (l'interfaccia sia
     * quindi reattiva).
     * 
     * Alla pressione del pulsante "down", il conteggio venga da lì in poi
     * aggiornato decrementandolo; alla pressione del pulsante "up", il
     * conteggio venga da lì in poi aggiornato incrementandolo; e così via, in
     * modo alternato.
     * 
     * Alla pressione del pulsante "stop", il conteggio si blocchi, e i tre
     * pulsanti vengano disabilitati. Per far partire l'applicazioni si tolga il
     * commento nel main qui sotto.
     * 
     * Suggerimenti: - si mantenga la struttura dell'esercizio precedente - per
     * pilotare la direzione su/giù si aggiunga un flag booleano all'agente --
     * deve essere volatile? - la disabilitazione dei pulsanti sia realizzata
     * col metodo setEnabled
     */
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JPanel panel;
    public final JButton up;
    public final JButton down;
    public final JButton stopBtn;
    public JLabel label;
    public ConcurrentGUI() {
        panel = new JPanel();
        up = new JButton("up");
        down = new JButton("down");
        stopBtn = new JButton("stop");
        label = new JLabel("");
        panel.add(label);
        panel.add(up);
        panel.add(down);
        panel.add(stopBtn);
        this.getContentPane().add(panel);
        this.setVisible(true);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        final Agent agent = new Agent();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Executor ex = new ThreadPerTaskExecutor();
        //ex.execute(agent);
        stopBtn.addActionListener(l -> {
            agent.stopC();
            up.setEnabled(false);
            down.setEnabled(false);
            stopBtn.setEnabled(false);
        });
        down.addActionListener(l -> {
            agent.sign = false;
        });
        up.addActionListener(l -> {
            agent.sign = true;
        });
    }


public class Agent implements Runnable {
    protected static final int TIME = 100;
    protected volatile int count;
    //true : pos, false: neg
    private volatile boolean sign = true;
    protected volatile boolean stop;
    @Override
    public void run() {
        while (!stop) {
            try {
                ConcurrentGUI.this.label.setText(Integer.toString(Agent.this.count));
                checkSign();
                Thread.sleep(TIME);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }
    protected void checkSign() {
        if (sign) {
            this.count++;
        } else {
            this.count--;
        }
    }
    public void stopC() {
       this.stop = true;
    }
}
public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
      new Thread(r).start();
    }
  }
}
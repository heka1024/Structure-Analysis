package structure;

import algebra.Matrix;
import chart.GraphPanel;
import force.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller extends JPanel {
    final static int MESH = 100;
    public static void main(String[] args) {
        try {
            File file;
            file = new File("./g3.input");
            Scanner sc = new Scanner(file);
            List<Node> nodes = new ArrayList<>();
            List<Element> elements = new ArrayList<>();

            int nodeSize = sc.nextInt(), elementSize = sc.nextInt(),
                cfSize = sc.nextInt(), dfSize = sc.nextInt(),
                nodeForces = sc.nextInt(), momentForces = sc.nextInt();

            for (int i = 0; i < nodeSize; i++) {
                int id = sc.nextInt();
                double x = sc.nextDouble(), y = sc.nextDouble();
                int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
                nodes.add(Node.of(x, y, a, b, c));
            }

            for (int i = 0; i < elementSize; i++) {
                double l = sc.nextDouble(), angle = sc.nextDouble();
                int a = sc.nextInt(), e = sc.nextInt(), ii = sc.nextInt();
                int p = sc.nextInt(), q = sc.nextInt();
                elements.add(Element.of(l, angle, a, e, ii, p, q));
            }

            final Structure structure = Structure.of();
            structure.elementList = elements;
            structure.nodeSize = nodeSize;
            structure.nodes = nodes;

            for (int i = 0; i < cfSize; i++) {
                int at = sc.nextInt() - 1;
                double q = sc.nextDouble(), x = sc.nextDouble();
                structure.elementList.get(at).loads.add(ConcentratedForce.of(q, x));
            }

            for (int i = 0; i < dfSize; i++) {
                int at = sc.nextInt() - 1;
                double q1 = sc.nextDouble(), q2 = sc.nextDouble(),
                        x1 = sc.nextDouble(), x2 = sc.nextDouble();
                structure.elementList.get(at).loads.add(DistributedForce.of(q1, q2, x1, x2));
            }

            for (int i = 0; i < nodeForces; i++) {
                int at = sc.nextInt();
                double q = sc.nextDouble();
                structure.nodeForces.add(NodeForce.of(at, q));
            }

            for (int i = 0; i < momentForces; i++) {
                int at = sc.nextInt();
                double q = sc.nextDouble();
                structure.nodeMoments.add(NodeMoment.of(at, q));
            }

            structure.buildGlobalK();
            structure.buildGlobalP();

//            System.out.println(structure.buildGlobalDVector());
            final Matrix globalK = structure.getGlobalKMatrix();
            System.out.println("---------- global K ----------");
            System.out.println(globalK);
            structure.buildGlobalDVector();
            System.out.println("---------- global D ----------");
            System.out.println(structure.getGlobalDVector());
            structure.distributeForce();
            System.out.println("-----------------------------");

            List<Double> xs = new ArrayList<>();
            List<List<Double>> ss = new ArrayList<>();
            List<List<Double>> bb = new ArrayList<>();

            for (Element element : structure.elementList) {
                List<Double> sfdGraph = new ArrayList<>();
                List<Double> bmdGraph = new ArrayList<>();
                System.out.println("-------- local F ---------------");
                System.out.println(element.localF);
                System.out.println("----------- shear force -------------");
                System.out.println(element);
                for (double x = 0; x <= element.l; x += element.l / MESH) {
                    xs.add(x);
                    double shearForce = element.localF.get(1);
                    double moment = element.localF.get(2);
                    for (Force f : element.loads) {
                        shearForce -= f.shearForce(x);
                    }
                    sfdGraph.add(shearForce);
                    for (double sfd : sfdGraph) {
                        moment -= sfd * (element.l / MESH);
                    }
                    bmdGraph.add(moment);
                    System.out.println(x + ", " + shearForce + ", " + moment);
                }
                ss.add(sfdGraph);
                bb.add(bmdGraph);
            }
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Many charts same frame");
                frame.setLayout(new GridLayout(2, 4, 5, 5));
                for (int i = 0; i < ss.size(); i++) {
                    final int n = i + 1;
                    frame.getContentPane().add(new GraphPanel(xs, ss.get(i), "sfd of element" + n));
                    frame.getContentPane().add(new GraphPanel(xs, bb.get(i), "bmd of element" + n));
                }
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

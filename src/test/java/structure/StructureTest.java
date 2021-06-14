package structure;

import algebra.Matrix;
import algebra.Vec;
import force.ConcentratedForce;
import force.DistributedForce;
import force.NodeForce;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import util.Pair;

import static org.assertj.core.api.BDDAssertions.then;

class StructureTest {
    @Test
    void test_build_global_kmatrix() {
        final Structure s = Structure.of(
                Element.of(5, 90, 1, 1, 1),
                Element.of(9.9, 45, 1, 1, 1),
                Element.of(12, -90, 1, 1, 1)
        );
        final Matrix k = s.getGlobalKMatrix();
        then(k.rowSize()).isEqualTo(12);
        then(k.columnSize()).isEqualTo(12);
    }

    @Test
    void test_build_global_pmatrix() {
        final Structure s = Structure.of(
                Element.of(5, 90, 1, 1, 1, 1, 2),
                Element.of(9.9, 45, 1, 1, 1, 2, 3),
                Element.of(12, -90, 1, 1, 1, 3, 4)
        );
        s.nodeSize = 4;
        final Element e = s.elementList.get(1);
//        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        e.loads.add(f);
        s.nodeForces.add(NodeForce.of(2, -90.0));
        final Vec u = s.getGlobalPVector();
        System.out.println(u);
        then(u.get(4)).isCloseTo(-96.182, Offset.offset(0.001));
//        final Pair<Double, Double> p = f.momentAtElement(e);
//        final Vec v = Vec.of(0.000, 0.000, 0.000, -6.182, 6.182, 7.274, -0.182, 0.182, -0.817, 0.000, 0.000, 0.000);
//        then(s.getGlobalPVector()).isEqualTo(v);
    }

    @Test
    void test_build_kMatrix() {
        final Structure s = Structure.of(
                Element.of(6, 0, 1, 1, 1, 1, 3),
                Element.of(6, 60, 1, 1, 1, 1, 2),
                Element.of(6, -60, 1, 1, 1, 2, 3)
        );
        System.out.println(s.elementList);
        s.nodeSize = 3;
        final Matrix k = s.getGlobalKMatrix();
        then(k.rowSize()).isEqualTo(9);
        then(k.columnSize()).isEqualTo(9);
        System.out.println(k);
    }

    @Test
    void test_with_one_element() {
        final Structure s = Structure.of(
                Element.of(5, 0, 1, 1, 1, 1, 2)
//                Element.of(9.9, 45, 1, 100000, 1, 2, 3),
//                Element.of(12, -90, 1, 100000, 1, 3, 4)
        );
        s.nodeSize = 2;
        final Element e = s.elementList.get(0);
//        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        e.loads.add(f);
//        s.nodeForces.add(NodeForce.of(2, -90.0));
        final Matrix globalK = s.getGlobalKMatrix();
        System.out.println("---------- global K ----------");
        System.out.println(globalK);
        System.out.println("-----------------");

        System.out.println("--------- global P ----------");
        final Vec globalP = s.getGlobalPVector();
        System.out.println(globalP);
        System.out.println("-----------------");

        s.distributeForce();
        for (Element element : s.elementList) {
            System.out.println(element.localF);
        }
//        final Vec u = s.getGlobalPVector();
//        System.out.println(u);
//        then(u.get(4)).isCloseTo(-96.182, Offset.offset(0.001));
//        final Pair<Double, Double> p = f.momentAtElement(e);
//        final Vec v = Vec.of(0.000, 0.000, 0.000, -6.182, 6.182, 7.274, -0.182, 0.182, -0.817, 0.000, 0.000, 0.000);
//        then(s.getGlobalPVector()).isEqualTo(v);
    }

    @Test
    void test_example_1() {
        final Structure s = Structure.of(
                Element.of(3, 90, 1, 1, 1, 1, 2),
                Element.of(5, 36.869, 1, 1, 1, 2, 3),
                Element.of(8, 0, 1, 1, 1, 3, 4),
                Element.of(6, -90, 1, 1, 1, 4, 5)
        );
        s.nodeSize = 5;
        final Element e2 = s.elementList.get(1);

//        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(20 , 2);
        e2.loads.add(f);
        final Element e3 = s.elementList.get(2);
        final DistributedForce ff = DistributedForce.of(5, 5, 0, 8);
        e3.loads.add(ff);
//        e.loads.add(f);
//        s.nodeForces.add(NodeForce.of(2, -90.0));
        final Matrix globalK = s.getGlobalKMatrix();
        System.out.println("---------- global K ----------");
        System.out.println(globalK);

        System.out.println("-----------------");
        final Matrix m = globalK.subMatrix(3, 3, 9, 9);
        System.out.println(m);

        System.out.println("------------------------------------");
        s.nodes.add(Node.of(0, 0, 0));
        s.nodes.add(Node.of(1, 1, 1));
        s.nodes.add(Node.of(1, 1, 1));
        s.nodes.add(Node.of(1, 1, 1));
        s.nodes.add(Node.of(0, 0, 0));
        System.out.println("------------------------------------");
        final Pair<Matrix, Vec> pair = s.filterMatrix();
        System.out.println(pair.first);
        System.out.println("------------------------------------");
        System.out.println(pair.second);
        System.out.println("------------------------------------");
        System.out.println(pair.first.solve(pair.second));
        System.out.println("------------------------------------");
        System.out.println(s.buildGlobalDVector());

        s.distributeForce();
        System.out.println("-----------------------------");
        for (Element element : s.elementList) {
            System.out.println("-------- local F ---------------");
            System.out.println(element.localF);
        }

//
//        System.out.println("--------- global P ----------");
//        final Vec globalP = s.getGlobalPVector();
//        System.out.println(globalP);
//        System.out.println("-----------------");
//
//        s.distributeForce();
//        for (Element element : s.elementList) {
//            System.out.println(element.localF);
//        }
//        final Vec u = s.getGlobalPVector();
//        System.out.println(u);
//        then(u.get(4)).isCloseTo(-96.182, Offset.offset(0.001));
//        final Pair<Double, Double> p = f.momentAtElement(e);
//        final Vec v = Vec.of(0.000, 0.000, 0.000, -6.182, 6.182, 7.274, -0.182, 0.182, -0.817, 0.000, 0.000, 0.000);
//        then(s.getGlobalPVector()).isEqualTo(v);
    }

    @Test
    void test_distribute() {
        final Structure s = Structure.of(
                Element.of(5, 90, 1, 1000, 1, 1, 2),
                Element.of(9.9, 45, 1, 1000, 1, 2, 3),
                Element.of(12, -90, 1, 1000, 1, 3, 4)
        );
        s.nodeSize = 4;
        s.nodes.add(Node.of(0, 0, 0));
        s.nodes.add(Node.of(1, 1, 1));
        s.nodes.add(Node.of(1, 1, 1));
        s.nodes.add(Node.of(0, 0, 0));

        final Element e = s.elementList.get(1);
//        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        e.loads.add(f);
        s.nodeForces.add(NodeForce.of(2, -90.0));
        final Matrix globalK = s.getGlobalKMatrix();
        System.out.println("---------- global K ----------");
        System.out.println(globalK);
        System.out.println("-----------------");

        System.out.println("--------- global P ----------");
        final Vec globalP = s.getGlobalPVector();
        System.out.println(globalP);
        System.out.println("-----------------");
        s.buildGlobalDVector();
        s.distributeForce();
        for (Element element : s.elementList) {
            System.out.println(element.localF);
        }
//        final Vec u = s.getGlobalPVector();
//        System.out.println(u);
//        then(u.get(4)).isCloseTo(-96.182, Offset.offset(0.001));
//        final Pair<Double, Double> p = f.momentAtElement(e);
//        final Vec v = Vec.of(0.000, 0.000, 0.000, -6.182, 6.182, 7.274, -0.182, 0.182, -0.817, 0.000, 0.000, 0.000);
//        then(s.getGlobalPVector()).isEqualTo(v);
    }
}
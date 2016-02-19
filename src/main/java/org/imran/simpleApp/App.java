package org.imran.simpleApp;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.ign.cogit.cartagen.genealgorithms.polygon.PolygonSquaring;
import fr.ign.cogit.cartagen.genealgorithms.polygon.SquarePolygonLS;
import fr.ign.cogit.cartagen.genealgorithms.polygon.VisvalingamWhyatt;
import fr.ign.cogit.geoxygene.api.spatial.AbstractGeomFactory;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IPolygon;
import fr.ign.cogit.geoxygene.spatial.geomengine.GeometryEngine;
import fr.ign.cogit.geoxygene.util.conversion.ParseException;
import fr.ign.cogit.geoxygene.util.conversion.WktGeOxygene;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) throws ParseException {
    System.out.println("Hello World modified!");
    GeometryEngine.init();
    Logger.getLogger(PolygonSquaring.class).setLevel(Level.ERROR);
    AbstractGeomFactory factory = GeometryEngine.getFactory();
    String poly = "POLYGON((342769.09260294178966433 7685297.82426011189818382,342768.9703088240348734 7685303.96953952684998512,342770.46841176593443379 7685305.98739246837794781,342770.8352941190241836 7685299.2612159950658679,342770.77414706017589197 7685297.6408189358189702,342769.09260294178966433 7685297.82426011189818382))";

    IPolygon pol = (IPolygon) WktGeOxygene.makeGeOxygene(poly);
    PolygonSquaring ps = new PolygonSquaring(pol);
    SquarePolygonLS sq = new SquarePolygonLS(10, 0.15, 7);
    VisvalingamWhyatt vis = new VisvalingamWhyatt(5);

    sq.setPolygon(pol);

    IPolygon polf = sq.square();
    System.out.println("LS - " + polf);
    polf = ps.simpleSquaring();
    System.out.println("simple - " + polf);
    pol = factory.createIPolygon(vis.simplify(pol.exteriorLineString()));
    System.out.println("Visval " + pol);
  }
}

package org.imran.newBidule;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

import com.vividsolutions.jts.geom.Geometry;

public class BigThing {

  public static List<SimpleFeature> getListFeatures(String shapePath)
      throws Exception {
    List<SimpleFeature> feats = new ArrayList<>();
    File file = new File(shapePath);
    Map<String, Object> map = new HashMap<>();
    map.put("url", file.toURI().toURL());
    DataStore dataStore = DataStoreFinder.getDataStore(map);
    String typeName = dataStore.getTypeNames()[0];
    FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore
        .getFeatureSource(typeName);
    Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM,
    FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source
        .getFeatures(filter);
    try (FeatureIterator<SimpleFeature> features = collection.features()) {
      while (features.hasNext()) {
        SimpleFeature feature = features.next();
        // System.out.print(feature.getID());
        // System.out.println(": " + feature.getAttribute("name"));
        // System.out.println(feature.getDefaultGeometryProperty().getValue());
        feats.add(feature);
      }
      features.close();
    }
    dataStore.dispose();
    return feats;
  }

  public static List<String> intersectors(List<SimpleFeature> feats) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < feats.size() - 1; ++i) {
      for (int j = i + 1; j < feats.size(); ++j) {
        Geometry gi = (Geometry) feats.get(i).getDefaultGeometry();
        Geometry gj = (Geometry) feats.get(j).getDefaultGeometry();
        if (gi.intersects(gj) && !gi.touches(gj)) {
          // System.out.println("candidate for " + i + " and " + j);
          // System.out.println("IDPAR: " + feats.get(i).getAttribute("IDPAR")
          // + " -- IDPAR: " + feats.get(j).getAttribute("IDPAR"));
          result.add(i + "inter " + j + " => IDPAR: "
              + feats.get(i).getAttribute("IDPAR") + " -- IDPAR: "
              + feats.get(j).getAttribute("IDPAR"));
        }
      }
    }
    return result;
  }

  public static List<Path> path_find(String pathDir) throws Exception {
    final List<Path> files = new ArrayList<>(1000);
    int nCycles = 50;
    for (int i = 0; i < nCycles; i++) {
      Path dir = Paths.get(pathDir);

      files.clear();
      files.addAll(
          Files.find(dir, 1, (path, attrs) -> true /* attrs.isDirectory() */)
              .collect(Collectors.toList()));
    }
    files.remove(0); // remove itself
    return files;
  }

  public static void main(String[] args) throws Exception {
    String sep = File.separator;
    // String numrep = "770589452";// "77007270";
    String numrep = "testoss";// "77007270";
    String foldName = "/media/imran/Data_2/IUDF/77_long_simuls/";
    foldName = "/media/imran/Data_2/klodo/77/COGIT28112016/dep77/";
    // String foldName = "/media/imran/Data_2/IUDF/";
    String parcellName = "parcelle_new.shp";
    // String parcellName = "tests.shp";

    // List<SimpleFeature> feats = getListFeatures(
    // foldName + numrep + sep + parcellName);
    // intersectors(feats);

    List<Path> paths = path_find(foldName);
    int c = 0;
    int tot = 0;
    for (Path p : paths) {
      // System.out.println("-------" + p.toString() + "--------------");
      List<SimpleFeature> feats = getListFeatures(
          p.toString() + sep + parcellName);
      List<String> res = intersectors(feats);
      if (res.size() > 0) {
        // System.out.println(p);
        c++;
      }
      // System.out.println("-------" + p.toString() + "--------------\n");
      ++tot;
      if (tot % 100 == 0) {
        System.out.print(tot + ".. ");
      }
    }
    System.out.println();
    System.out.println("done");
    System.out.println(c + "/" + paths.size());
  }
}

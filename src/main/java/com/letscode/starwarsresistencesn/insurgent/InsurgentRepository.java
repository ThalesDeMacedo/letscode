package com.letscode.starwarsresistencesn.insurgent;

import com.letscode.starwarsresistencesn.insurgent.model.Insurgent;
import com.letscode.starwarsresistencesn.insurgent.projection.ReportProjection;
import com.letscode.starwarsresistencesn.insurgent.projection.ResourceProjection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface InsurgentRepository extends CrudRepository<Insurgent, Integer> {
    @Query(value = "SELECT " +
            "COUNT(IC.ID) TOTAL, SUM(CASE WHEN RENEGADE THEN 1 ELSE 0 END) AS RENEGADE " +
            "FROM (" +
            "SELECT I.ID AS ID, COUNT(RENEGADE_ID) > 2 AS RENEGADE " +
            "FROM INSURGENT I " +
            "LEFT JOIN REPORT_RENEGADE ON RENEGADE_ID = I.ID " +
            "GROUP BY I.ID) AS IC",
            nativeQuery = true)
    ReportProjection reportQuantities();

    @Query(value = "SELECT ITEM, SUM(QUANTITY) QUANTITY " +
            "FROM ( " +
            "         SELECT IM.MAP_KEY AS ITEM, SUM(MAP) QUANTITY " +
            "         FROM INSURGENT I " +
            "                  INNER JOIN INSURGENT_MAP IM ON I.ID = IM.INSURGENT_ID " +
            "                  LEFT JOIN REPORT_RENEGADE ON RENEGADE_ID = I.ID " +
            "         GROUP BY I.ID, IM.MAP_KEY " +
            "         HAVING COUNT(RENEGADE_ID) < 3 " +
            "     ) AS R " +
            "GROUP BY ITEM", nativeQuery = true)
    List<ResourceProjection> reportResourceByInsurgent();

    @Query(value = "SELECT MAP_KEY AS ITEM, SUM(MAP) AS QUANTITY " +
            "FROM ( " +
            "         SELECT DISTINCT I.ID, MAP_KEY, MAP " +
            "         FROM INSURGENT_MAP " +
            "                  INNER JOIN INSURGENT I ON I.ID = INSURGENT_MAP.INSURGENT_ID " +
            "                  INNER JOIN REPORT_RENEGADE ON RENEGADE_ID = I.ID " +
            "     ) AS R GROUP BY MAP_KEY", nativeQuery = true)
    List<ResourceProjection> reportResourceByRenegade();
}

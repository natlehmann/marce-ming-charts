SELECT t.songId, s.name, t.performerId, p.name,  r.licensorId, l.name, sum(u.units) as currentAmount

FROM BMATDigitalChartsDB.Usage u, Track t, Song s, BMATDigitalChartsDB.Release r, Licensor l, Performer p
WHERE u.trackId = t.id
AND t.songId = s.id
AND t.performerId = p.id
AND t.releaseId = r.id
AND r.licensorId = l.id

AND u.chartDate between {ts'2015-12-01 00:00:00.0'} and {ts'2015-12-31 00:00:00.0'}
and u.rightId = 4
and u.countryId = 2
group by t.songId, t.performerId
order by currentAmount desc;





SELECT * from WeeklyReportItem i, WeeklyReport r
where i.weeklyReport_id = r.id
AND r.enabled = true
AND r.country_id = 1
AND r.right_id = 1
AND r.dateFrom < {ts'2016-12-25 00:00:00.0'}
AND i.songId = 2147
AND i.performerId = 238
group by r.country_id, r.right_id, i.songId, i.performerId



SELECT sum(i.currentAmount) from MonthlyReportItem i, MonthlyReport r
where i.monthlyReport_id = r.id
AND r.enabled = true
AND r.country_id = 1
AND r.right_id = 4
AND r.dateFrom <= {ts'2016-12-25 00:00:00.0'}
AND i.songId = 2484
AND i.performerId = 281
AND r.filteredBySource_id IS NULL




SELECT DISTINCT (u.sourceId) 
FROM BMATDigitalChartsDB.Usage u, Track t
WHERE u.trackId = t.id
and u.chartDate between {ts'2015-11-06 00:00:00.0'} and {ts'2015-12-10 00:00:00.0'}
and u.rightId = 1
and u.countryId = 1
AND t.songId IN (2486, 1073, 2485, 2487, 2147, 862, 2148, 2483, 2149, 1280, 2150, 2289, 2151, 2152, 931, 1010, 501, 2428, 934, 
2369, 1124, 1203, 1003, 588, 880, 879, 598, 596, 2283, 658, 1023, 2066, 624, 652, 992, 745, 611, 1227, 1275, 809, 1274, 2761, 
2484, 808, 1237, 2465, 2429, 533, 2363, 2308, 2307, 1034, 612, 647, 983, 984, 890, 1011, 2170, 597, 830, 1245, 960, 2803, 2373, 
720, 1125, 887, 2372, 994, 2093, 532, 600, 995, 1252, 858, 2493, 987, 1228, 544, 2802, 2368, 528, 884, 502, 2562, 975, 1191, 
649, 2371, 2172, 591, 1282, 659, 2138, 1268, 498, 1012, 1335, 1251, 2582, 1810, 831, 650, 2804, 2362, 1208, 1132, 1241, 2827, 
1249, 2095, 958, 2563, 2285, 729, 2565, 988, 748, 959, 797, 1716, 535, 1759, 527, 2583, 594, 2443, 561, 2176, 1126, 2137, 1218, 
1243, 531, 2786, 2492, 1259, 1212, 1177, 1761, 2764, 2398, 1229, 1333, 1253, 560, 2828, 1171, 1242, 1152, 499, 1244, 625, 2311, 
1209, 2785, 1133, 2423, 2488, 507, 562, 2566, 889, 529, 1115, 2284, 2139, 2295, 864, 500, 1234, 1134, 2768, 1225, 1717, 1762, 
543, 1135, 1136, 1189, 1223, 746, 800, 1224, 2496, 1713, 2568, 2073, 1035, 1261, 914, 916, 1235, 1764, 593, 2297, 894, 1190, 2489);





SELECT DISTINCT(u.sourceuri)
FROM BMATDigitalChartsDB.Usage u, Track t, Song s, Performer p
WHERE u.trackId = t.id
AND t.songId = s.id
AND t.performerId = p.id

--AND u.chartDate between {ts'2015-12-01 00:00:00.0'} and {ts'2015-12-31 00:00:00.0'}
and u.rightId = 4
--and u.countryId = 2
and t.songId = 100
and t.performerId = 98
and u.sourceId = 5 --BMatTopTracks
group by t.songId, t.performerId


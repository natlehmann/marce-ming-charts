select t.songId, sum(t.total_streams) as amount, s.name, p.name, t.label, ch.restSourceId
from TopChartDetail t, Song s, Performer p, ChartHeader ch
where s.id = t.songId
and p.id = t.performerId
and ch.id = t.chartHeaderId
and ch.countryId = 1
GROUP by t.songId, t.label
order by amount desc
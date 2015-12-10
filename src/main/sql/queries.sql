select u.trackId, sum(u.units) as amount, t.songId, s.name, t.performerId, p.name, r.labelCompanyId, l.name
from BMATDigitalChartsDB.Usage u, Track t, Song s, Performer p, BMATDigitalChartsDB.Release r, LabelCompany l
where t.id = u.trackId
and s.id = t.songId
and p.id = t.performerId
and r.id = t.releaseId
and l.id = r.labelCompanyId
and u.chartDate between {ts'2015-10-30 00:00:00.0'} and {ts'2015-11-05 00:00:00.0'}
and u.rightId = 1
and u.countryId = 2
group by u.trackId
order by amount desc



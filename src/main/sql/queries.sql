SELECT t.songId, s.name, t.performerId, p.name, sum(u.units) as currentAmount, r.labelCompanyId, l.name

FROM BMATDigitalChartsDB.Usage u, Track t, Song s, BMATDigitalChartsDB.Release r, LabelCompany l, Performer p
WHERE u.trackId = t.id
AND t.songId = s.id
AND t.performerId = p.id
AND t.releaseId = r.id
AND r.labelCompanyId = l.id

AND u.chartDate between {ts'2015-11-06 00:00:00.0'} and {ts'2015-12-10 00:00:00.0'}
and u.rightId = 1
and u.countryId = 1
group by s.id
order by currentAmount desc



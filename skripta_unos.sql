INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (1, 'Nogomet - Muški', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (2, 'Nogomet - Ženski', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (3, 'Košarka - Muški', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (5, 'Košarka - Ženski', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (6, 'Stolni Tenis - Muški', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (7, 'Stolni Tenis - Ženski', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (8, 'Odbojka - Muški', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (9, 'Odbojka - Ženski', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (10,'Rukomet - Muški', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (12, 'Rukomet - Ženski', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (13, 'Objektno orijentirano programiranje', 'OOP', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (14, 'Šah', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (15, 'Elektronika 1', 'Ele1', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (16, 'Matematika 1', 'Mat1', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (17, 'Teorija elektriènih krugova', 'TEK', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (18, 'Analiza elektroenergetskih sistema','AES', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (19, 'Engleski jezik', 'Eng', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (20, 'Informatika', 'Inf', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (21, 'Telekomunikacije', 'Tel', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (23, 'Osnove Elektrotehnike', 'OE', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (24, 'Automatika', 'Aut', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (25, 'Fizika', 'Fiz', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (26, 'Elektronika 2', 'Ele2', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (27, 'Obnovljivi izvori Energije', 'OIE', 0):
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (28, 'Matematika 2', 'Mat2', 0);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (30, 'Kros', NULL, 1);
INSERT INTO Category(Id, Name, Nick, IsSport) VALUES (31, 'Veslanje', NULL, 1);

INSERT INTO Competition(Id, TimeFrom, TimeTo, CategoryId, Location, IsAssumption) VALUES (1,'2015-05-15 09:15:00', '2015-05-15 12:00:00', 15, 'Hotel MOC', 0);
INSERT INTO Competition(Id, TimeFrom, TimeTo, CategoryId, Location, IsAssumption) VALUES (2,'2015-05-20 11:00:00', '2015-05-20 14:00:00', 16, 'Hotel MOC', 0);

INSERT INTO Faculty(Id, Name) VALUES (1, 'ETF Podgorica');
INSERT INTO Faculty(Id, Name) VALUES (2, 'ETF Banja Luka');
INSERT INTO Faculty(Id, Name) VALUES (3, 'FOI Varaždin');
INSERT INTO Faculty(Id, Name) VALUES (4, 'ETF Beograd');
INSERT INTO Faculty(Id, Name) VALUES (5, 'ETF Istoèno Sarajevo');
INSERT INTO Faculty(Id, Name) VALUES (6, 'FEIT Skoplje');
INSERT INTO Faculty(Id, Name) VALUES (7, 'ELFAK Niš');
INSERT INTO Faculty(Id, Name) VALUES (8, 'ICT Beograd');
INSERT INTO Faculty(Id, Name) VALUES (9, 'ETF Osijek');
INSERT INTO Faculty(Id, Name) VALUES (10, 'FTN Kosovska Mitrovica');
INSERT INTO Faculty(Id, Name) VALUES (11, 'FER Zagreb');
INSERT INTO Faculty(Id, Name) VALUES (12, 'FTN Novi Sad');
INSERT INTO Faculty(Id, Name) VALUES (13, 'TVZ Zagreb');
INSERT INTO Faculty(Id, Name) VALUES (14, 'FESB Split');
INSERT INTO Faculty(Id, Name) VALUES (15, 'FTN Èaèak');
INSERT INTO Faculty(Id, Name) VALUES (16, 'FINKI Skoplje');

INSERT INTO Stage(Id, Name) VALUES (1, 'Skupine');
INSERT INTO Stage(Id, Name) VALUES (2, 'Osmina finala');
INSERT INTO Stage(Id, Name) VALUES (3, 'Èetvrtfinale');
INSERT INTO Stage(Id, Name) VALUES (4, 'Polufinale');
INSERT INTO Stage(Id, Name) VALUES (5, 'Finale');
INSERT INTO Stage(Id, Name) VALUES (6, 'Za 3. mjesto');

INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified) 
VALUES (1, 'ETF BG 1', NULL, NULL, 0, NULL, 4, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (2, 'Aleksandar', 'Lazoviæ', NULL, 1, 1, 4, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (3, 'Ivan', 'Mitiæ', NULL, 1, 1, 4, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (4, 'Nikola', 'Milanoviæ', NULL, 1, 1, 4, 1, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (5, 'ETF BG 2', NULL, NULL, 0, NULL, 4, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (6, 'ETF SAR 2', NULL, NULL, 0, NULL, 5, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (7, 'ETF BG 1', NULL, NULL, 0, NULL, 4, 2, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (8, 'Jelena', 'Trišoviæ', NULL, 1, 7, 4, 2, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (9, 'ETF SAR 1', NULL, NULL, 0, NULL, 5, 2, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (10, 'ETF SAR 2', NULL, NULL, 0, NULL, 5, 2, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (11, 'ELFAK Niš', NULL, NULL, 0, NULL, 7, 2, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (12, 'Zlatan', 'Tucakoviæ', NULL, 1, 9, 5, 2, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (13, 'Amar', 'Èivgin', NULL, 1, 10, 5, 2, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (14, 'ETF PG', NULL, 'A', 0, NULL, 1, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (15, 'ETF BL', NULL, 'A', 0, NULL, 2, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (16, 'FOI Var', NULL, 'A', 0, NULL, 3, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (17, 'ETF BG', NULL, 'B', 0, NULL, 4, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (19, 'ETF Sar1', NULL, 'B', 0, NULL, 5, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (20, 'FEIT SK', NULL, 'B', 0, NULL, 6, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (21, 'ETF Niš', NULL, 'B', 0, NULL, 7, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (22, 'ICT BG', NULL, 'C', 0, NULL, 8, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (23, 'ETF Sar2', NULL, 'C', 0, NULL, 5, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (24, 'ETF OS', NULL, 'C', 0, NULL, 9, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (25, 'FTN KM', NULL, 'C', 0, NULL, 10, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (26, 'FER ZG', NULL, 'D', 0, NULL, 11, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (27, 'FTN NS', NULL, 'D', 0, NULL, 12, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (28, 'TVZ ZG', NULL, 'D', 0, NULL, 13, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (29, 'FESB ST', NULL, 'A', 0, NULL, 14, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (30, 'FER ZG', NULL, 'A', 0, NULL, 11, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (31, 'ETF Niš', NULL, 'A', 0, NULL, 7, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (32, 'TVZ ZG', NULL, 'A', 0, NULL, 13, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (33, 'ETF Sar', NULL, 'A', 0, NULL, 5, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (34, 'FESB ST', NULL, 'B', 0, NULL, 14, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (35, 'FEIT SK', NULL, 'B', 0, NULL, 6, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (36, 'ETF BG', NULL, 'B', 0, NULL, 4, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (37, 'FTN NS', NULL, 'B', 0, NULL, 12, NULL, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (38, 'ETF SAR1', NULL, NULL, 0, NULL, 5, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (39, 'FER ZG1', NULL, NULL, 0, NULL, 11, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (40, 'Rade', 'Milanoviæ', NULL, 1, 5, 4, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (41, 'Živojin', 'Miriæ', NULL, 1, 5, 4, 1, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (42, 'Stefan', 'Savièeviæ', NULL, 1, 5, 4, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (43, 'Emir', 'Avdukiæ', NULL, 1, 10, 5, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (44, 'Dženan', 'Palajiæ', NULL, 1, 10, 5, 1, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (45, 'Haris', 'Aliæ', NULL, 1, 10, 5, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (46, 'Enis', 'Misimoviæ', NULL, 1, 38, 5, 1, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (47, 'Edin ', 'Vršajeviæ', NULL, 1, 38, 5, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (48, 'Avdija', 'Džeko', NULL, 1, 38, 5, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (49, 'Petar', 'Lopasiæ', NULL, 1, 39, 11, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (50, 'Tibor', 'Kovaè', NULL, 1, 39, 11, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (51, 'Branko', 'Horvat', NULL, 1, 39, 11, 1, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (52, 'FER ZG2', NULL, NULL, 0, NULL, 11, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (53, 'Silvio', 'Modriæ', NULL, 1, 52, 11, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (54, 'Robert', 'Zirdum', NULL, 1, 52, 11, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (55, 'Ivana', 'Lisac', NULL, 1, 52, 11, 1, 3, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (56, 'TVZ ZG', NULL, NULL, 0, NULL, 13, 1, NULL, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (57, 'Tomislav', 'Ivošiæ', NULL, 1, 56, 13, 1, 1, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (58, 'Josip', 'Petrušiè', NULL, 1, 56, 13, 1, 2, 0);
INSERT INTO Competitor(Id, Name, Surname, Section, IsPerson, GroupCompetitorId, FacultyId, CompetitionId, OrdinalNum,IsDisqualified)
VALUES (59,N'Alfred', 'Brezni', NULL, 1, 56, 13, 1, 3, 0);

INSERT INTO User(Id, Name, Surname, LoginType) VALUES (1, 'Sebastian', 'Glad', 'FER');

INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (1, 61.0000, NULL, 1, 1, 2, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (2, 67.0000, NULL, 1, 1, 3, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (3, 97.0000, NULL, 1, 1, 4, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (7, 60.0000, NULL, 2, 1, 8, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (8, 60.0000, NULL, 2, 1, 12, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (9, 60.0000, NULL, 2, 1, 13, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (13, 74.0000, NULL, 1, 1, 40, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (14, 68.0000, NULL, 1, 1, 41, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (15, 56.0000, NULL, 1, 1, 42, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (16, 90.0000, NULL, 1, 1, 43, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (17, 77.0000, NULL, 1, 1, 44, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (18, 22.0000, NULL, 1, 1, 45, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (19, 70.0000, NULL, 1, 1, 46, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (20, 63.0000, NULL, 1, 1, 47, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (21, 41.0000, NULL, 1, 1, 48, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (22, 85.0000, NULL, 1, 1, 49, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (23, 54.0000, NULL, 1, 1, 50, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (24, 46.0000, NULL, 1, 1, 51, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (25, 87.0000, NULL, 1, 1, 53, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (26, 91.0000, NULL, 1, 1, 54, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (27, 98.0000, NULL, 1, 1, 55, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (28, 41.0000, NULL, 1, 1, 57, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (29, 43.0000, NULL, 1, 1, 58, 0, 1);
INSERT INTO CompetitionScore(Id, Result, Note, CompetitionId, UserId, CompetitorId, IsAssumption, IsOfficial) VALUES (30, 37.0000, NULL, 1, 1, 59, 0, 1);

INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption) 
VALUES (1,'2015-05-20 09:00:00', NULL, 1, 14, 15, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption) 
VALUES (2,'2015-05-20 09:30:00', NULL, 1, 16, 29, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (9,'2015-05-20 10:00:00', NULL, 1, 17, 19, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (10,'2015-05-20 10:30:00', NULL, 1, 20, 21, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (12,'2015-05-20 11:00:00', NULL, 1, 22, 23, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (13,'2015-05-20 11:30:00', NULL, 1, 24, 25, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (14,'2015-05-20 12:00:00', NULL, 1, 26, 27, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (15,'2015-05-20 12:30:00', NULL, 1, 14, 16, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (16,'2015-05-20 13:00:00', NULL, 1, 15, 29, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (17,'2015-05-20 13:30:00', NULL, 1, 17, 20, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (18,'2015-05-20 14:00:00', NULL, 1, 19, 21, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (19,'2015-05-20 14:30:00', NULL, 1, 22, 24, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (20,'2015-05-20 15:00:00', NULL, 1, 23, 25, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (21,'2015-05-20 15:30:00', NULL, 1, 26, 28, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (22,'2015-05-20 16:00:00', NULL, 1, 14, 29, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (23,'2015-05-20 16:30:00', NULL, 1, 15, 16, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (24,'2015-05-20 17:00:00', NULL, 1, 17, 21, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (25,'2015-05-20 17:30:00', NULL, 1, 19, 20, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (26,'2015-05-20 18:00:00', NULL, 1, 22, 25, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption) 
VALUES (27,'2015-05-20 18:30:00', NULL, 1, 23, 24, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (28,'2015-05-20 19:00:00', NULL, 1, 27, 28, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (29,'2015-05-22 09:00:00', NULL, 1, 29, 21, 3, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (30,'2015-05-22 09:30:00', NULL, 1, 24, 28, 3, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (31,'2015-05-22 10:00:00', NULL, 1, 16, 19, 3, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (32,'2015-05-22 10:30:00', NULL, 1, 22, 26, 3, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (33,'2015-05-22 11:00:00', NULL, 1, 29, 28, 4, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption) 
VALUES (34,'2015-05-22 11:30:00', NULL, 1, 19, 26, 4, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (35,'2015-05-22 15:30:00', NULL, 1, 28, 19, 6, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (36,'2015-05-22 16:00:00', NULL, 1, 29, 26, 5, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (37,'2015-05-21 09:30:00', NULL, 5, 30, 31, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (38,'2015-05-21 10:00:00', NULL, 5, 32, 33, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (40,'2015-05-21 10:30:00', NULL, 5, 34, 35, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (41,'2015-05-21 11:00:00', NULL, 5, 36, 37, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (42,'2015-05-21 11:30:00', NULL, 5, 30, 32, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (43,'2015-05-21 12:00:00', NULL, 5, 31, 33, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (44,'2015-05-21 12:30:00', NULL, 5, 34, 36, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (45,'2015-05-21 13:00:00', NULL, 5, 35, 37, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (46,'2015-05-21 16:00:00', NULL, 5, 30, 33, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (47,'2015-05-21 16:30:00', NULL, 5, 31, 32, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (48,'2015-05-21 17:00:00', NULL, 5, 34, 37, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (49,'2015-05-21 17:30:00', NULL, 5, 35, 36, 1, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (50,'2015-05-23 15:30:00', NULL, 5, 33, 34, 4, 'Hotel Beèiæi', 0);
INSERT INTO Duel(Id, TimeFrom, TimeTo, CategoryId, Competitor1Id, Competitor2Id, StageId, Location, IsAssumption)
VALUES (51,'2015-05-23 16:00:00', NULL, 5, 30, 36, 4, 'Hotel Beèiæi', 0);

INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (1, 3.0000, 1.0000, 1, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (2, 0.0000, 1.0000, 2, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (3, 2.0000, 3.0000, 9, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (4, 0.0000, 1.0000, 10, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (5, 3.0000, 0.0000, 12, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (6, 4.0000, 1.0000, 13, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (7, 4.0000, 0.0000, 14, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (8, 1.0000, 3.0000, 15, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (9, 0.0000, 2.0000, 16, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (10, 0.0000, 0.0000, 17, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (11, 3.0000, 0.0000, 18, 1, 0, 'B.B.', 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (12, 2.0000, 2.0000, 19, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (13, 3.0000, 2.0000, 20, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (14, 1.0000, 0.0000, 21, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (15, 2.0000, 3.0000, 22, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (16, 3.0000, 3.0000, 23, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (17, 1.0000, 1.0000, 24, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (18, 4.0000, 3.0000 25, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (19, 0.0000, 3.0000, 26, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (20, 1.0000, 5.0000, 27, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (21, 0.0000, 1.0000, 28, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (22, 1.0000, 0.0000, 29, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (23, 1.0000, 3.0000, 30, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (24, 2.0000, 4.0000, 31, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (25, 0.0000, 3.0000, 32, 1, 0, 'B.B.', 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (26, 3.0000, 2.0000, 33, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (27, 0.0000, 1.0000, 34, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (28, 0.0000, 2.0000, 35, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (29, 1.0000, 3.0000, 36, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (30, 41.0000, 13.0000, 37, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (31, 23.0000, 39.0000, 38, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (32, 28.0000, 20.0000, 40, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (33, 14.0000, 39.0000, 41, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (34, 29.0000, 28.0000, 42, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (35, 20.0000, 34.0000, 43, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (36, 11.0000, 43.0000, 44, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (37, 44.0000, 8.0000, 45, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (38, 18.0000, 28.0000, 46, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (39, 27.0000, 31.0000, 47, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (40, 42.0000, 7.0000, 48, 1, 0, NULL, 1);
INSERT INTO DuelScore(Id, Score1, Score2, DuelId, UserId, IsAssumption, Note, IsOfficial) VALUES (41, 19.0000, 27.0000, 49, 1, 0, NULL, 1);




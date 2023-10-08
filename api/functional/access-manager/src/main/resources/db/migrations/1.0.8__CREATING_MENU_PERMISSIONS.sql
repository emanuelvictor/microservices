   INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (400, now(), 'root.menu', 'Cardápio', 1);
 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (15000, now(), 'root.menu.diaEscolar', 'Dia escolar', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (150001, now(), 'root.menu.diaEscolar.adicionar', 'Adicionar dia escolar', 15000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (150002, now(), 'root.menu.diaEscolar.remover', 'Remover dia escolar', 15000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (16000, now(), 'root.menu.calendarioEscolar', 'Calendário escolar', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (160001, now(), 'root.menu.calendarioEscolar.adicionar', 'Adicionar calendário escolar', 16000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (160002, now(), 'root.menu.calendarioEscolar.copiarPreparacoes', 'Copiar preparações', 16000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (160003, now(), 'root.menu.calendarioEscolar.finalizar', 'Remover calendário escolar', 16000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (160004, now(), 'root.menu.calendarioEscolar.alterar', 'Alterar calendário escolar', 16000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (17000, now(), 'root.menu.receita', 'Receita', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (170001, now(), 'root.menu.receita.adicionar', 'Adicionar receita', 17000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (170002, now(), 'root.menu.receita.alterar', 'Alterar receita', 17000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (170003, now(), 'root.menu.receita.remover', 'Remover receita', 17000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (18000, now(), 'root.menu.regra', 'Regra', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (180001, now(), 'root.menu.regra.ativar', 'Ativar regra', 18000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (180002, now(), 'root.menu.regra.inativar', 'Inativar regra', 18000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (19000, now(), 'root.menu.preparacao', 'Preparação', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (190001, now(), 'root.menu.preparacao.adicionar', 'Adicionar preparação', 19000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (190002, now(), 'root.menu.preparacao.alterar', 'Alterar preparação', 19000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (190003, now(), 'root.menu.preparacao.remover', 'Remover preparação', 19000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (20000, now(), 'root.menu.preparacaoDoDiaEscolar', 'Preparação do dia escolar', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (200001, now(), 'root.menu.preparacaoDoDiaEscolar.adicionar', 'Adicionar preparação do dia escolar', 20000);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (200002, now(), 'root.menu.preparacaoDoDiaEscolar.remover', 'Remover preparação do dia escolar', 20000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (21000, now(), 'root.menu.matriculaPorPeriodo', 'Matrícula por período', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (210001, now(), 'root.menu.matriculaPorPeriodo.alterar', 'Alterar matrícula por período', 21000);

 INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (22000, now(), 'root.menu.pesquisaPropria', 'Pesquisa própria', 400);
INSERT INTO permission (id, created_on, authority, name, upper_permission_id) VALUES (220001, now(), 'root.menu.pesquisaPropria.adicionar', 'Adicionar pesquisa própria', 22000);

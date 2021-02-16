use hotel;

-- Insere tuplas em Funcionario
insert into funcionario (CPFFuncionario, NomeFuncionario,TelefoneFuncionario,
						EnderecoFuncionario, SalarioFuncionario, TipoFuncionario,
                        SexoFuncionario, LoginUsuario, SenhaUsuario, 
                        DataNascimentoFuncionario, CPFSupervisor) 
                        values ('12019220628', 'Raphael Rodrigues', '31987656573', 'Rua Santa Catarina, Nº75, Bairro Novo Amazonas, João Monlevade-MG', 10000.00, 'Administrador', 'Masculino', 'admin', 'admin', '1997-01-07', '12019220628'),
							   ('11954570651', 'Lucas Horta Monteiro de Castro', '31986693624','Rua do Horto, Nº35, Bairro Marcos Rocha, João Monlevade-MG', 5000, 'Gerencia', 'Masculino', 'LucasCastro', '9696LucasCastro', '1996-10-12','12019220628'),
							   ('19885635822', 'Flavia Helena', '31998563652','Rua 6, Nº36, Bairro Anchieta, Joao Monlevade, MG', 5000.00, 'Gerencia', 'Feminino', 'HelenaFlavia', 'HelenaFlavia', '1996-04-08','12019220628');
                               
                       
insert into funcionario (CPFFuncionario, NomeFuncionario,TelefoneFuncionario,
						EnderecoFuncionario, SalarioFuncionario, TipoFuncionario,
                        SexoFuncionario, DataNascimentoFuncionario, CPFSupervisor) 
                        values ('12178598725', 'Fernando Ribeiro', '31985263212', 'Rua Monte Santo, Nº 200, Bairro Bela Vista, João Monlevade-MG', '2000', 'Limpeza', 'Masculino', '1985-05-01', '15485635822'),
                               ('14763274597', 'Marcela Alves', '31987564521', 'Rua Alfredo Sampaio, Nº 1020, AP 101, João Monlevade-MG', '3500', 'Restaurante', 'Feminino', '1975-08-21', '11954570651'),
							   ('16778569855', 'Matheus Gomes ', '31986693624','Rua Mato Grosso, Nº 35, Bairro Amazonas, Itabira-MG', 2500.00, 'Segurança', 'Masculino', '1974-10-10','11954570651'),
                               ('20546532011', 'Henrique Barcelos ', '31986859988','Rua Tadeu Cardoso, Nº 1700, Bairro Amazonas, Itabira-MG', 3000.00, 'Bar', 'Masculino', '1985-11-07','11954570651');

insert into servico (TipoServico, Descricao, valorServico, CPFFuncionario) 
				  values ('Limpeza', 'Lavar', 8, 12178598725),
						 ('Limpeza', 'Passar', 7, 12178598725),
                         ('Bar', 'Cerveja 600ml', 4.50, 20546532011),
                         ('Bar', 'Refrigerante 350ml', 3.50, 20546532011),
                         ('Bar', 'Dose vodka', 10.00, 20546532011),
                         ('Bar', 'Vinho 1L', 70.00, 20546532011),
                         ('Bar', 'Dose whisky', 20.00, 20546532011),
                         ('Restaurante', 'Filé parmegiana', 50, '14763274597'),
                         ('Restaurante', 'Salmão grelhado', 70, '14763274597'),
                         ('Restaurante', 'Lasanha', 30, '14763274597'),
                         ('Restaurante', 'Spaghetti', 20, '14763274597'),
                         ('Restaurante', 'Feijão tropeiro', 25, '14763274597'),
                         ('Restaurante', 'Feijoada', 40, '14763274597');

-- Insere tuplas em Quarto
insert into quarto (CodQuarto, StatusQuarto, TipoQuarto) 
				  values ('101', 'Disponível', 'Single'),
					     ('102', 'Disponível', 'Double'),
						 ('201', 'Disponível', 'Single'),
						 ('202', 'Disponível', 'Double'),
						 ('301', 'Disponível', 'Família'),
						 ('302', 'Disponível', 'Família'),
						 ('401', 'Disponível', 'Single'),
						 ('402', 'Disponível', 'Double'),
						 ('501', 'Disponível', 'Família'),
						 ('502', 'Disponível', 'Luxo'),
                         ('601', 'Disponível', 'Luxo'),
                         ('602', 'Disponível', 'Luxo');

-- Insere tuplas em Hospede

insert into hospede (CPFHospede, NomeHospede, TelefoneHospede, EnderecoHospede, SexoHospede, DataNascHospede) 
				  values ('15496585233', 'Joao Victor Murad', '31984454323', 'Rua Durval Eloi da Fonseca, Nº 210, Mangabeiras, Belo Horizonte-MG', 'Masculino', '1990-02-07'),
						 ('48565895241', 'Winder Philipe', '31987654342', 'Rua Fernando Vasconcelos, Nº 500, Lourdes, Ipatinga-MG', 'Masculino', '1986-12-03'),
						 ('69512545218', 'Amanda Julia Duarte', '31976675876', 'Rua Jose Eloi, Nº 22, Centro, Itabira-MG', 'Feminino', '1974-07-26'),
						 ('65248598566', 'Eduarda Rufino', '31987654324', 'Rua Sao Paulo, Nº 10, Rosário, Ouro Preto-MG', 'Feminino', '1979-04-14');

-- Insere tuplas em Dependentes

insert into dependente (CPFHospede, NomeDependente, ParentescoDependente, SexoDependente, DataNascimentoDependente) 
				  values ('15496585233', 'Camila Julia Duarte', 'Filha', 'Feminino', '2004-02-07'),
                         ('15496585233', 'Pedro Philipe', 'Filho', 'Masculino', '2006-07-20'),
                         ('48565895241', 'Fabricia Philipe', 'Filha', 'Feminino', '2008-03-04'),
                         ('69512545218', 'Vitor Murad', 'Filho', 'Masculino', '2004-06-15');
                         
 -- Insere tuplas em Reserva

insert into reserva (CodReserva, CPFHospede, CPFFuncionario, CodQuarto, DataEntrada, DataSaida, DataHoraAddReserva, StatusReserva) 
				  values ('001', '15496585233', '11954570651', '201', '2019-06-02', '2019-06-20', '2019-02-20 16:40','Disponível'),
                         ('002', '48565895241', '11954570651', '101', '2019-05-10', '2019-05-25', '2019-04-10 12:20', 'Disponível'),
                         ('003', '69512545218', '19885635822', '202', '2019-03-20', '2019-04-10', '2019-02-10 17:40', 'Disponível'),
                         ('004', '65248598566', '19885635822', '602', '2019-06-01', '2019-08-20','2019-05-30 08:30', 'Disponível');  
                         		
 -- Insere tuplas em HospedagemDiaria

insert into hospedagemdiaria (CodReserva, valorDiaria) 
				  values ( '001', 100),
                         ( '002', 100),
                         ( '003', 170),
                         ( '004', 350);  	

-- Insere tuplas em HospedagemServico

insert into hospedagemservico (CodReserva, valorTotalServico, CodServico) 
				  values ( '001', 80, 1),
						 ( '001', 70, 2),
                         ( '003', 120, 7),
                         ( '002', 70, 2),
                         ( '003', 50, 5),
                         ( '003', 50, 5),
                         ( '004', 100, 7);
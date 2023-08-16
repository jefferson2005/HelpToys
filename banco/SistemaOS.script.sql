/**
* Sistema para gentão de OS 
* @author Jefferson Lins de Oliveira
*/
create database dbsistema;

use dbsistema;

-- unique (não permiti valores duplicados no campo)
create table usuarios( 
	id int primary key auto_increment, 
	nome varchar(30) not null, 
	login varchar(20) not null unique,
	senha varchar(250) not null,
    perfil varchar(10) not null
 );
 
 
 describe usuarios;
 
 show tables;

 describe usuarios;
 
 insert into usuarios (nome,login,senha,perfil) values 
('Jefferson Lins','Jeff',md5('123@senac'), 'user');

insert into usuarios (nome,login,senha,perfil) values 
('Mario armado','mario@armado',md5('123@senac'),'admin');

select * from usuarios where login = "admin";

select *from usuarios;

show databases;

delete from usuarios where id = 6;

-- login (autenticação)
select * from usuarios where nome = 'admin' and senha = md5('admin');

-- busca avançada pelo nome (estilo google)
select nome from usuarios where nome like 'j%' order by nome;


-- tabela produtos
insert into fornecedor ( nome,fone,email,cnpj,cep,endereco,numero,complemento,bairro,cidade,uf) value
('jeff','234342','fjl@gmai.com','123123','03819250','23','tudo','tudo','jkçsa','sao pau','sp');

CREATE TABLE fornecedor (
    idfornecedores INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    fone VARCHAR(15) NOT NULL,
    email VARCHAR(30) NULL ,
    cnpj VARCHAR(11) NOT NULL UNIQUE,
    cep VARCHAR(10),
    endereco VARCHAR(50) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(20),
    bairro VARCHAR(30) NOT NULL,
    cidade VARCHAR(30) NOT NULL,
    uf CHAR(2) NOT NULL
    );
    
    insert into produtos (nomeprodutos,descricao,estoque,estoquemin,valor,numeromedida,localarmazenagem,idfornecedores)value
('Boneca','Boneca Grande',200,50,200,1,'Estoque','1');
    
    create table produtos(
	idproduto int primary key auto_increment,
	nomeprodutos varchar(20) not null,
	barcode varchar (30) null,
	descricao varchar (200) not null,
	foto longblob null,
	estoque int  not null,
	estoquemin int  not null,
	valor decimal(10,2) not null,
	numeromedida varchar(50) not null,
	localarmazenagem varchar(50) not null,
    idprodutor int null,
	foreign key (idprodutor) references fornecedor(idfornecedores)
);

    
    
select 
produtos.idproduto,
produtos.descricao,
produtos.foto,
produtos.estoque,
produtos.estoquemin,
produtos.valor,
produtos.numeromedida,
produtos.localarmazenagem
from produtos
inner join fornecedor
on produtos.idprodutor = fornecedor.idfornecedores;	

select * from produtos;

select * from fornecedor;

use dbsistema;

desc fornecedor;
desc produtos;

drop table fornecedor;
drop table produtos;

/**
* Agenda de contatos
* @author Jefferson Lins de Oliveira
*/

-- comentários em linha

-- exibir banco de dados 
show databases;

-- apagar um banco de dados
drop database dbagenda;

-- criar um novo banco de dados
create database dbagenda;

-- selecionar o banco de dados a ser trabalhado
use dbagenda; 

-- criando uma tabela no banco de dados
-- int (tipo de dados: número inteiro)
-- pruimary key (chave primária)
-- auto_incrimente (numeração automática)
-- varchar (tipo de dados: String)
-- not null (campo obrigatório)

create table contatos( 
	id int primary key auto_increment, 
	nome varchar(30) not null, 
	fone varchar(15) not null,
	email varchar(50) 
 );

show tables;

-- descrever a tabela
describe contatos;

-- selecionar tudo da tabela
select * from contatos;

--  CRUD Create --
insert into contatos (nome,fone,email) value 
('Jefferson Lins','959695806-0909','Jefferson960@gmail.com');

insert into contatos (nome,fone)
value ('Bill Gates',' 9999999-00000');

insert into contatos(nome,fone,email)
value ('Jefferson Lins','8698543-3593','Jefferson@Jeff');

insert into contatos(nome,fone)
value ('Joyce Lins','523578-8252');

insert into contatos(nome,fone)
value ('João Oliveira','458295-92852');

insert into contatos(nome,fone)
value ('Júlia Oliveira','8235725-2194');

insert into contatos(nome,fone)
value ('Mario Armado','298592-582');

insert into contatos(nome,fone)
value ('Luigi Mario','69965-8549');

insert into contatos(nome,fone)
value ('Pierre Lins','29825-034');

insert into contatos(nome,fone)
value ('Loucura Talvez','59825-55252');

-- simulando um erro
insert into contatos(nome,fone,email)
values('Robson Vaamonde','9839458-395848','vava@email.com');

insert into contatos(nome,fone)
values('Leandrão Raminho', '9090909-090909');

-- CRUD Read --
-- selecionar tudo na tabela
select *from contatos;

-- selecionar e ordenar 
select * from contatos order by nome;
select * from contatos order by nome desc;

-- pesquisas avançadas
select nome from contatos;
select nome,email from contatos; 
select * from contatos where nome like 'j%';
select * from contatos where nome = 'Mario Armado';

select * from contatos where id = 1;

-- relatórios personalizados com apelidos para os campos --

select nome as Contato, fone as Telefone, email as E_mail from contatos order by nome;


-- CRUD Update --
-- CUIDADO !!! (Sempre usar a cláusula where junto a chave primária)

update contatos set fone = '19858-2985' where id =1;
update contatos set fone = '99999-43132', email = 'Sissa@gmail.com' where id = 3;
update contatos set nome = 'Mario armado', fone = '99999-00000', email = 'Mariomuitoarmado@gmail.com' where id = 7;

-- Auterções --
update contatos set email = 'Joãorato@gmail.com', nome = 'JOGOBRla' where id = 5;
update contatos set email = 'Billgates@hotmail.com', nome = 'José', fone = '111111-0000' where id = 2;
update contatos set email = 'LoucuraProvavel@gmail.com' where id = 10;
update contatos set email = 'Desnecessario@gmail.com' where id = 8;
update contatos set email = 'Steam2gmail.com', fone = '6666666-333333' where id = 4;

-- CRUD delete --
-- CUIDADO !!! (Sempre usar a cláusula where junto a chave primária) --

delete from contatos where id = 1;

-- Alterações na estrutura da tabela --
alter table contatos add column obs varchar (100);

-- Adicionar uma coluna após um local específico
 alter table contatos add fone2 varchar(15) not null after fone;
 
 -- aletrando tipo de dados ou validações
 
 -- ATENÇÃO aos dados já cadastrados! 
 
 alter table contatos modify fone2 varchar(20);
 
 -- Excluir uma coluna 
 alter table contatos drop column obs;
 
 -- Excluir a tabela inteira 
 drop table contatos;
 
describe contatos;

select * from contatos;



use dbsistema;




insert into clientes (nome,fone,email,cpf) value 
('Jefferson Lins','959695806-0909','Jefferson960@gmail.com','12314145');


update clientes set fone = '1958-2985' where id =1;


update clientes set fone = '99999-43132', email = 'Sissa@gmail.com' where id = 1;


update clientes set nome = 'Mario armado', fone = '99999-00000', email = 'Mariomuitoarmado@gmail.com' where id = 7;


update clientes set nome = 'Jefferson Lins', fone = '99999-00000', email = 'jefferson@gamil.com', cpf = '123' where id = 1;


drop table servicos2;


select * from clientes;



Drop table clientes;

describe clientes;

select * from clientes;

create table clientes(
idcli int primary key auto_increment,
nome varchar (30) not null,
fone varchar (15) not null unique,
email varchar (30) null unique,
cpf varchar (11) not null unique,
cep varchar(10),
endereco varchar(50) not null,
numero varchar(10) not null,
complemento varchar(20),
bairro varchar(30) not null,
cidade varchar(30) not null,
uf char(2) not null
);





/* Relacionamento de tabelas 1 - N */

-- timestamp default current_timestamp (data e hora automática)
-- decimal (números não inteiros) 10,2 (dígitos,casas decimais)
-- fereign key(FK) chave estrangeira
-- 1 (FK) --------- N (PK)

create table servicos(
	os int primary key auto_increment,
    dataOS timestamp default current_timestamp,
    brinquedo varchar(200) not null,
    defeito varchar(200) not null,
    valor decimal (10,2),
    id int not null,
    foreign key (id) references clientes(idcli)
    );
    
   
    


insert into servicos (brinquedo,defeito,valor,idcli)
values ('Notebook Lenovo G90','Troca da fonte',250,5);

select * from servicos;




update servicos set brinquedo = 'jojo', defeito = 'nenhum', valor = '200.00' where os = 1;

delete from clientes where idcli = 8;



/** RELATÓRIOS **/

select nome,fone from clientes order by nome,email;
select nome,fone,email from clientes order by nome;

-- servicos
select 
servicos.os,
servicos.dataOS,
servicos.defeito,
servicos.valor,
clientes.nome
from servicos
inner join clientes
on servicos.id = clientes.idcli;


-- selecionando o conteúdo de 2 ou mais tabelas
select * from servicos
inner join clientes
on servicos.id = clientes.idcli;


   


















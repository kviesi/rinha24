
CREATE UNLOGGED TABLE carteira(
  id bigserial primary key,
  nome varchar(255),
  limite int check (limite > 0),
  valor int default 0
);

CREATE TYPE tipo_transacao AS ENUM ('c', 'd');

CREATE UNLOGGED TABLE transacoes(
    id bigserial primary key,
    valor int,
    descricao varchar(255) not null,
    realizada_em timestamp default now(),
    tipo tipo_transacao,
    id_clientes bigint references carteira(id)
);

create index idx_transacoes_id_client on transacoes(id_clientes);
create index idx_transacoes_realizada_em on transacoes(realizada_em);

DO $$
BEGIN
  INSERT INTO carteira (nome, limite)
  VALUES
    ('o barato sai caro', 1000 * 100),
    ('zan corp ltda', 800 * 100),
    ('les cruders', 10000 * 100),
    ('padaria joia de cocaia', 100000 * 100),
    ('kid mais', 5000 * 100);
END; $$
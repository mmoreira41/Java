# 📦 Scripts SQL – Banco de Dados `roombooking_db`

Este diretório contém os scripts SQL responsáveis pela criação e inicialização do banco de dados utilizado no projeto **RoomBooking**, um sistema de reserva de salas com gerenciamento de recursos e clientes.

---

## 🧩 Modelo Relacional (Diagrama ER)

Abaixo está o diagrama relacional utilizado como base para modelagem do banco de dados:
![DER labPM (2)](https://github.com/user-attachments/assets/54c2ffed-1805-43f6-b140-ac743116162e)


## 🗂 Arquivos disponíveis

### `criar_bd.sql`
> Script principal que realiza:
- Criação do banco `roombooking_db`
- Definição do tipo `ENUM` personalizado (`tipo_sala`)
- Criação das tabelas:
  - `Sala`
  - `Recurso`
  - `Sala_Recurso` (tabela associativa N:N)
  - `Cliente`
  - `Reserva`
- Definição de chaves primárias e **relacionamentos (foreign keys)**

---

## 🛠 Pré-requisitos

- PostgreSQL instalado localmente (versão 12+ recomendada)
- Um cliente SQL (como pgAdmin, VS Code com extensão PostgreSQL, DBeaver etc.)
- Uma conexão ativa com o servidor local

---

## ▶️ Como usar

1. Certifique-se de estar conectado ao PostgreSQL.
2. Execute a linha abaixo em uma aba de query:

   ```sql
   CREATE DATABASE roombooking_db;

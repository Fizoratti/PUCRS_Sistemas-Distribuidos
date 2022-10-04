## Trabalho Final

Modelo cliente/servidor e P2P utilizando programação com Sockets

- Implementar sistema P2P básico
- Arquitetura centralizada
- Um único programa, dois modos: peer, server
- Informações de configuração por parâmetro na carga do programa

### TODO

**Commands** (Menu para ler e executar comandos pelo terminal)

- [ ] Join (registrar no servidor para poder trocar com outros peers)
  - Peer informa lista de recursos disponíveis (um diretório com arquivos [recursos])
- [ ] List (retorna uma estrutura de dados (lista) de recursos disponíveis [|nome|hash|IP-origem|porta|])
- [ ] Search -resource "file.txt" ou Search -hash "8dqg9h4" (deve retornar os metadados do recurso [|nome|hash|IP-origem|porta|])
  - Critério de busca iterando sobre a estrutura de dados de recursos disponíveis

**Features**

- [x] _Heartbeat_ com o servidor (10s)
= [ ] Um único programa, dois modos: peer, server
- [ ] Generate hash from a file 
- [ ] Critério de busca
- [ ] Comunicação de um peer com outro peer para obter um recurso
- [ ] Incluir ou excluir um peer e seus recursos da estrutura de dados de recursos
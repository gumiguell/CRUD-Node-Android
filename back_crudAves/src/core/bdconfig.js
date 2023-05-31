require("dotenv/config")
module.exports = {
    // crie uma variável de ambiente no Windows chamada
    // NODE_MYSQL_SERVIDOR e faça ela valer o nome ou o
    // endereço IP da máquina que executa o MySQL (se for
    // uma instalação local, faça ela valer LOCALHOST)
    host: process.env.HOST,
  
    // crie uma variável de ambiente no Windows chamada
    // NODE_MYSQL_USUARIO e faça ela valer o seu login
    user: process.env.USER,
  
    // crie uma variável de ambiente no Windows chamada
    // NODE_MYSQL_SENHA e faça ela valer sua senha
    password: process.env.PASSWORD,
  
    // crie uma variável de ambiente no Windows chamada
    // NODE_MYSQL_BD e faça ela valer nome do BD que
    // pretende acessar
    database: process.env.DATABASE
  };
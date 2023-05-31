const bd = require ('../core/bd.js');

async function inclua (ave)
{
    const conexao = await bd.getConexao ();
    if (conexao==null) return null;

    try
    {
        const sql     = 'INSERT INTO aves (id, nomeCientifico, nome, apelido, link) VALUES (?,?,?,?,?)';
                        //"INSERT INTO usuarios(`nome`, `email`, `fone`, `data_nascimento`) VALUES(?)";
        const dados   = [ave.id,ave.nomeCientifico,ave.nome,ave.apelido,ave.link];
        await conexao.query (sql, dados);
        return true;
    }
    catch (excecao)
    {
        return false;
    }
}

async function atualize (ave)
{
    const conexao = await bd.getConexao ();
    if (conexao == null) return null;

    try
    {
        const sql   = 'UPDATE aves SET nomeCientifico=?,nome=?,apelido=?,link=? WHERE id=?';
        const dados = [ave.nomeCientifico,ave.nome,ave.apelido,ave.link,ave.id];
        await conexao.query (sql,dados);
        return true;
    }
    catch (excecao)
    {
        return false;
    }
}
    
async function remova (id)
{
    const conexao = await bd.getConexao ();
    if (conexao==null) return null;

    try
    {
        const sql   = 'DELETE FROM aves WHERE id=?';
        const dados = [id];
        await conexao.query (sql,dados);
        return true;
    }
    catch (excecao)
    {
        return false;
    }
}
 
async function recupereUm (id)
{
    const conexao = await bd.getConexao();
    if (conexao==null) return null;

    try
    {
        const  sql     = 'SELECT * FROM aves WHERE id=?';
        const  dados   = [id];
        const [linhas] = await conexao.execute(sql,dados);
        return linhas;
    }
    catch (excecao)
    {
        return false;
    }
}

async function recupereTodos ()
{
    const conexao = await bd.getConexao();
    if (conexao==null) return null;

    try
    {
        const  sql     = 'SELECT * FROM aves';
        const [linhas] = await conexao.query(sql);
        return linhas;
    }
    catch (excecao)
    {
        return false;
    }
}

module.exports = {inclua, atualize, remova, recupereUm, recupereTodos}




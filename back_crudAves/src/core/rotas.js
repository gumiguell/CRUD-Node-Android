const Aves     = require ('../daos/aves.js');
const Ave      = require ('../dbos/ave.js');
const Comunicado = require ('./comunicado.js');

async function inclusao (req, res)
{
    if (Object.values(req.body).length!=5 || !req.body.id || !req.body.nomeCientifico || !req.body.nome || !req.body.apelido || !req.body.link)
    {
        const erro = Comunicado.novo('DDI','Dados incorretos','Não foram fornecidos exatamente as 4 informações esperadas de uma ave (id, nome científico, nome, apelido)').object;
        return res.status(422).json(erro);
    }
    
    let ave;
    try
    {
        ave = Ave.novo (req.body.id,req.body.nomeCientifico,req.body.nome,req.body.apelido,req.body.link);
    }
    catch (excecao)
    {
        const erro = Comunicado.novo('TDE','Dados de tipos errados','Id deve ser um numero natural positivo. Nome científico, nome, apelido e link devem ser textos não vazios').object;
        return res.status(422).json(erro);
    }

    const ret = await Aves.inclua(ave);

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('AJE','Ave já existe','Já há uma ave cadastrada com o id informado').object;
        return res.status(409).json(erro);
    }

  //if (ret===true)
  //{
    const  sucesso = Comunicado.novo('IBS','Inclusão bem sucedida','A ave foi incluída com sucesso').object;
    return res.status(201).json(sucesso);
  //}
}

// para a rota de UPDATE
async function atualizacao (req, res)
{
    if (Object.values(req.body).length!=5 || !req.body.id || !req.body.nomeCientifico || !req.body.nomeCientifico || !req.body.apelido || !req.body.link)
    {
        const erro = Comunicado.novo('DdI','Dados inesperados','Não foram fornecidos exatamente as 5 informações esperadas de uma ave (id, nome científico, nome, apelido e link)').object;
        return res.status(422).json(erro);
    }
    
    let ave;
    try
    {
        ave = Ave.novo (req.body.id,req.body.nomeCientifico,req.body.nome,req.body.apelido,req.body.link);
    }
    catch (excecao)
    {
        const erro = Comunicado.novo('TDE','Dados de tipos errados','Id deve ser um numero natural positivo. Nome científico, nome e apelido devem ser textos não vazios').object;
        return res.status(422).json(erro);
    }

    const id = req.params.id;
    
    if (id!=ave.id)
    {
        const erro = Comunicado.novo('TMC','Mudança de código','Tentativa de mudar o código da ave').object;
        return res.status(400).json(erro);    
    }
    
    let ret = await Aves.recupereUm(id);

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('FNC','Falha no comando SQL','O comando SQL apresenta algum erro').object;
        return res.status(409).json(erro);
    }

    if (ret.length==0)
    {
        const erro = Comunicado.novo('ANE','Ave inexistente','Não há ave cadastrada com o id informado').object;
        return res.status(404).json(erro);
    }

    ret = await Aves.atualize(ave);

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('FNC','Falha no comando SQL','O comando SQL apresenta algum erro').object;
        return res.status(409).json(erro);
    }

  //if (ret===true)
  //{
        const sucesso = Comunicado.novo('ABS','Alteração bem sucedida','A ave foi atualizada com sucesso').object;
        return res.status(201).json(sucesso);
  //}
}

// para a rota de DELETE
async function remocao (req, res)
{
    if (Object.values(req.body).length!=0)
    {
        const erro = Comunicado.novo('DSP','Fornecimento de dados sem propósito','Foram fornecidos dados sem necessidade no corpo da requisição').object;
        return res.status(422).json(erro);
    }
    
    const id = req.params.id;
    let ret = await Aves.recupereUm(id);

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('FNC','Falha no comando SQL','O comando SQL apresenta algum erro').object;
        return res.status(409).json(erro);
    }

    if (ret.length==0)
    {
        const erro = Comunicado.novo('ANE','Ave inexistente','Não há ave cadastrada com o id informado').object;
        return res.status(404).json(erro);
    }

    ret = await Aves.remova(id);

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('FNC','Falha no comando SQL','O comando SQL apresenta algum erro').object;
        return res.status(409).json(erro);
    }

  //if (ret===true)
  //{
        const sucesso = Comunicado.novo('RBS','Remoção bem sucedida','A ave foi removida com sucesso').object;
        return res.status(200).json(sucesso);
  //}    
}

// para a segunda rota de READ (um)
async function recuperacaoDeUm (req, res)
{
    if (Object.values(req.body).length!=0)
    {
        const erro = Comunicado.novo('DSP','Fornecimento de dados sem propósito','Foram fornecidos dados sem necessidade no corpo da requisição').object;
        return res.status(422).json(erro);
    }

    const id = req.params.id;

    const ret = await Aves.recupereUm(id);

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('FNC','Falha no comando SQL','O comando SQL apresenta algum erro').object;
        return res.status(409).json(erro);
    }

    if (ret.length==0)
    {
        const erro = Comunicado.novo('ANE','Ave inexistente','Não há ave cadastrada com o id informado').object;
        return res.status(404).json(erro);
    }

    return res.status(200).json(ret);
}

// para a primeira rota de READ (todos)
async function recuperacaoDeTodos (req, res)
{
    if (Object.values(req.body).length!=0)
    {
        const erro = Comunicado.novo('DSP','Fornecimento de dados sem propósito','Foram fornecidos dados sem necessidade no corpo da requisição').object;
        return res.status(422).json(erro);
    }

    const ret = await Aves.recupereTodos();

    if (ret===null)
    {
        const  erro = Comunicado.novo('CBD','Sem conexão com o BD','Não foi possível estabelecer conexão com o banco de dados').object;
        return res.status(500).json(erro);
    }

    if (ret===false)
    {
        const  erro = Comunicado.novo('FNC','Falha no comando SQL','O comando SQL apresenta algum erro').object;
        return res.status(409).json(erro);
    }

    return res.status(200).json(ret);
}

module.exports = {inclusao, atualizacao, remocao, recuperacaoDeUm, recuperacaoDeTodos}
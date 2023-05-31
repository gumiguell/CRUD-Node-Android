class Ave
{
    #id
    #nomeCientifico
    #nome
    #apelido
    #link

    constructor (id, nomeCientifico, nome, apelido, link)
    {
        this.id = id;
        this.nomeCientifico = nomeCientifico;
        this.nome = nome;
        this.apelido = apelido;
        this.#link = link;
    }

    get id ()
    {
        return this.#id;
    }
    
    get nomeCientifico ()
    {
        return this.#nomeCientifico;
    }

    get nome ()
    {
        return this.#nome;
    }

    get apelido ()
    {
        return this.#apelido;
    }

    get link ()
    {
        return this.#link;
    }


    set id (id)
    {
        if (id===undefined || typeof id !== 'number' || isNaN(id) || id!==parseInt(id) || id<=0)
            throw ('Id inválido');

        this.#id = id;
    }

    set nomeCientifico (nomeCientifico)
    {
        if (nomeCientifico===undefined || typeof nomeCientifico !== 'string' || nomeCientifico==="")
            throw ('Nome científico inválido');

        this.#nomeCientifico = nomeCientifico;
    }

    set nome (nome)
    {
        if (nome===undefined || typeof nome !== 'string' || nome==="")
            throw ('Nome inválido');

        this.#nome = nome;
    }

    set apelido (apelido)
    {
        if (apelido===undefined || typeof apelido !== 'string' || apelido==="")
            throw ('Apelido inválido')

        this.#apelido = apelido;
    }

    set link (link)
    {
        if (link===undefined || typeof link !== 'string' || link==="")
            throw ('Link inválido')

        this.#link = link;
    }

}

function novo (id, nomeCientifico, nome, apelido, link)
{
    return new Ave (id, nomeCientifico, nome, apelido, link);
}

module.exports = {novo};
const cep = document.querySelector("#cep")

const showData = (result)=>{
    for(const field in result){
        console.log(field)

        if (document.querySelector("#"+field)) {
            document.querySelector("#"+field).value = result[field]
        }
    }
}

cep.addEventListener("blur", (e)=>{
    /* Se caso fazer o formatador para o cep no input */
    let searchCep = cep.value.replace("-","") 

    const options= {
        method: 'GET',
        node:'cors',
        cache:'default'
    }

    fetch(`https://viacep.com.br/ws/${searchCep}/json/`,options)
    .then((response)=>{ response.json()
        .then(data=> showData(data))
    })
    .catch(e => console.log('Deu Erro: '+e,message))
    
})


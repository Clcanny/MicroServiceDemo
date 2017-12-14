const express = require('express')
const app = express()
const fetch = require('isomorphic-fetch')
const getNumber = () => fetch('http://localhost:4444/simple-client-service/addParamsInPath/1/1').then((res) => res.json())
app.get('/health', (req, res) => {  
    res.json({
	status: 'UP'
    })
})
app.get('/number', function(request, response) {
    // response.send('{number: 1}')
    response.json( { number: 1 } )
})
app.get('/number2', function(request, response) {
    getNumber().then((x) => {
	response.send(x)
  }) 
})
app.listen(8000)

const express = require('express')
const app = express()
const fetch = require('isomorphic-fetch')
const getNumber = () => fetch('http://115.159.199.121:4444/simple-client-service/number').then((res) => res.json())
app.get('/health', (req, res) => {  
    res.json({
	status: 'UP'
    })
})
app.get('/number', function(request, response) {
    response.send('{number: 1}')
})
app.get('/number2', function(request, response) {
    getNumber().then((x) => {
	response.send(x)
  }) 
})
app.listen(8000)

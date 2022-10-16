using FuelQueManagement_Service.Models;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Bson;
using MongoDB.Driver;

namespace FuelQueManagement_Service.Controllers;

[ApiController]
[Route("[controller]")]
public class FuelController : ControllerBase
{
    // POST: FuelController/Create
    [HttpPost]
    public ActionResult Create(FuelModel response) 
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");

        IMongoCollection<FuelModel> collection = _db.GetCollection<FuelModel>("fuel");

        try
        {

            FuelModel fuelModel = new FuelModel();
            fuelModel.Type = response.Type.ToString();
            fuelModel.Amount = response.Amount;
            fuelModel.LastModified = DateTime.Now;

            collection.InsertOneAsync(fuelModel);


            var results = collection.FindAsync(_ => true);
            Console.WriteLine(results.ToString());

            return CreatedAtAction(nameof(GetFuelByID), new { id = results.Id }, results); ;
        }
        catch(Exception e)
        {
            Console.WriteLine(e.ToString());
        }

        return RedirectToAction(nameof(Index));

    }
    [HttpGet("{id}")]
    public async Task<List<FuelModel>> GetFuelByID(int id)
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");
        FuelModel fuel = new FuelModel();

        IMongoCollection<FuelModel> collection = _db.GetCollection<FuelModel>("fuel");

        var res =  await collection.FindAsync(c => c.Id.Equals(id));
        return res.ToList();


    }

}

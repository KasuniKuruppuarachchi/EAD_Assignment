using FuelQueManagement_Service.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualBasic;
using MongoDB.Bson;
using MongoDB.Driver;

namespace FuelQueManagement_Service.Controllers;

[ApiController]
[Route("[controller]")]
public class FuelController : ControllerBase
{
    // POST: FuelController/Create
    [HttpPost]
    public async Task<FuelStationModel> Create(FuelModel request) 
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");

        IMongoCollection<FuelStationModel> collection = _db.GetCollection<FuelStationModel>("fuelstation");

        try
        {
  
            FuelModel fuelModel = new FuelModel();
            fuelModel.Id = ObjectId.GenerateNewId().ToString();
            fuelModel.Type = request.Type.ToString();
            fuelModel.Amount = request.Amount;
            fuelModel.LastModified = DateTime.Now;
            fuelModel.StationsId = request.StationsId ?? null;

            //FuelModel[] fuel = new FuelModel[1];
            //fuel.Append(fuelModel);

            var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id,request.StationsId);

            var multiUpdateDefinition = Builders<FuelStationModel>.Update
                .Push(u => u.Fuel, fuelModel);

            var pushNotificationsResult = await collection.UpdateOneAsync(firstStationFilter, multiUpdateDefinition);


            //var results = collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();
            var results = collection.Find(i => i.Id == request.StationsId).ToList();

            return results[0];

        }
        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
        }

        return null;

    }
    [HttpGet("{id}")]
    public async Task<List<FuelModel>> GetFuelByID(string id)
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");
        FuelModel fuel = new FuelModel();

        IMongoCollection<FuelModel> collection = _db.GetCollection<FuelModel>("fuel");

        var res =  await collection.FindAsync(c => c.Id == id);
        Console.Write(res.ToString());
        return res.ToList();


    }

}

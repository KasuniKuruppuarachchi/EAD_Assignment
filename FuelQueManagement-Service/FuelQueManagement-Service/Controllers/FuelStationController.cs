using FuelQueManagement_Service.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;
using System.Globalization;

namespace FuelQueManagement_Service.Controllers;

[ApiController]
[Route("[controller]")]
public class FuelStationController : ControllerBase
{

    // POST: FuelStationController/Create
    [HttpPost]
    public FuelStationModel Create(FuelStationModel request)
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");

        IMongoCollection<FuelStationModel> collection = _db.GetCollection<FuelStationModel>("fuelstation");

        try
        {
            FuelStationModel fuelStation =  new FuelStationModel();
            fuelStation.Name = request.Name;
            fuelStation.Location = request.Location;
            fuelStation.StationOwner = request.StationOwner;
            fuelStation.LastModified = DateTime.Now;
            fuelStation.DieselStatus = false;
            fuelStation.PetrolStatus = false;
            fuelStation.Fuel = new FuelModel[0];
            fuelStation.Queue = new QueueModel[0];

            collection.InsertOneAsync(fuelStation);

            var res = collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

            return res[0];
        }
        catch
        {
            return null;
        }
    }

    [HttpGet]
    public async Task<List<FuelStationModel>> GetFuelStations()
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");
        FuelStationModel fuelStation = new FuelStationModel();

        IMongoCollection<FuelStationModel> collection = _db.GetCollection<FuelStationModel>("fuelstation");

        var res = collection.Find(_ => true).ToList();
        return res;
    }

    [HttpPost]
    [Route("UpdateDieselStatus")]
    public async Task<FuelStationModel> UpdateDieselStatus(bool status, string id)
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");
        FuelStationModel fuelStation = new FuelStationModel();

        IMongoCollection<FuelStationModel> collection = _db.GetCollection<FuelStationModel>("fuelstation");


            if(status == false)
            {
                fuelStation.PetrolStatus = true;
            }
            else
            {
                fuelStation.PetrolStatus = false;
            }
   

        var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, id);

        var updateNameDefinition = Builders<FuelStationModel>.Update
            .Set(u => u.DieselStatus, fuelStation.DieselStatus);

        var updateNameResult = await collection
            .UpdateOneAsync(firstStationFilter,
            updateNameDefinition);

        var res = collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

        return res[0];

    }

    [HttpPost]
    [Route("UpdatePetrolStatus")]
    public async Task<FuelStationModel> UpdatePetrolStatus(bool status, string id)
    {
        var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
        var _db = Client.GetDatabase("FuelQueue");
        FuelStationModel fuelStation = new FuelStationModel();

        IMongoCollection<FuelStationModel> collection = _db.GetCollection<FuelStationModel>("fuelstation");


            if (status == false)
            {
                fuelStation.PetrolStatus = true;
            }
            else
            {
                fuelStation.PetrolStatus = false;
            }

        var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, id);

        var updateNameDefinition = Builders<FuelStationModel>.Update
            .Set(u => u.PetrolStatus, fuelStation.PetrolStatus);

        var updateNameResult = await collection
            .UpdateOneAsync(firstStationFilter,
            updateNameDefinition);

        var res = collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

        return res[0];
    }
}

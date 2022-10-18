using FuelQueManagement_Service.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Bson;
using MongoDB.Driver;

namespace FuelQueManagement_Service.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class QueueController : Controller
    {


        // POST: QueueController/Create
        [HttpPost]
        public async Task<FuelStationModel> Create(QueueModel request)
        {
            var Client = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");
            var _db = Client.GetDatabase("FuelQueue");

            IMongoCollection<FuelStationModel> collection = _db.GetCollection<FuelStationModel>("fuelstation");

            try
            {
                QueueModel queueModel = new QueueModel();
                queueModel.Id = ObjectId.GenerateNewId().ToString();
                queueModel.VehicleType = request.VehicleType;
                queueModel.VehicleOwner = request.VehicleOwner;
                queueModel.StationsId = request.StationsId;
                queueModel.LastModified = DateTime.Now;

                var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, request.StationsId);

                var multiUpdateDefinition = Builders<FuelStationModel>.Update
                    .Push(u => u.Queue, queueModel);

                var result = await collection.UpdateOneAsync(firstStationFilter, multiUpdateDefinition);

                //var results = collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();
                var results = collection.Find(i => i.Id == request.StationsId).ToList();

                return results[0];

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                return null;
            }
        }
    }

}

using FuelQueManagement_Service.Models;
using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Driver;

namespace FuelQueManagement_Service.Services
{
    public class QueueService
    {
        //creating the database connection
        private readonly IMongoCollection<FuelStationModel> _Collection;
        public QueueService(IOptions<DatabaseConnection> datbaseConnection)
        {
            var mongoClient = new MongoClient(
                datbaseConnection.Value.ConnectionString);

            var mongoDatabase = mongoClient.GetDatabase(
                datbaseConnection.Value.DatabaseName);

            _Collection = mongoDatabase.GetCollection<FuelStationModel>(
                datbaseConnection.Value.CollectionName);
        }

        //This is required to create a queue object 
        public async Task<FuelStationModel> Create(QueueModel request)
        {
                QueueModel queueModel = new QueueModel();
                queueModel.Id = ObjectId.GenerateNewId().ToString();
                queueModel.VehicleType = request.VehicleType;
                queueModel.VehicleOwner = request.VehicleOwner;
                queueModel.FuelType = request.FuelType;
                queueModel.StationsId = request.StationsId;
                queueModel.LastModified = DateTime.Now;

                var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, request.StationsId);
                var multiUpdate = Builders<FuelStationModel>.Update
                    .Push(u => u.Queue, queueModel);
                var result = await _Collection.UpdateOneAsync(firstStationFilter, multiUpdate);
                var results = _Collection.Find(i => i.Id == request.StationsId).ToList();

                return results[0];
        }

        //This is required to delete a queue object 
        public async Task<FuelStationModel> Delete(string fuelType, string stationId, string vehicleOwner)
        {
                // create a filter
                var fuelStationObject = Builders<FuelStationModel>
                    .Filter.ElemMatch(t => t.Queue,
                    queue => queue.FuelType == fuelType);
                var pullVehicle = Builders<FuelStationModel>.Update
                    .PullFilter(t => t.Queue,
                        queue => queue.VehicleOwner == vehicleOwner);
                var result = await _Collection
                    .UpdateManyAsync(fuelStationObject, pullVehicle);
                var res = _Collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

                return res[0];
        }
    }
}

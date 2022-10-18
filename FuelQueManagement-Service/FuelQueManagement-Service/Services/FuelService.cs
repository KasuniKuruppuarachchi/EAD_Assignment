using FuelQueManagement_Service.Models;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Driver;
using System;

namespace FuelQueManagement_Service.Services
{
    public class FuelService
    {
        private readonly IMongoCollection<FuelStationModel> _Collection;
        private readonly IMongoCollection<FuelModel> _FuelCollection;
        public FuelService(IOptions<DatabaseConnection> datbaseConnection)
        {
            var mongoClient = new MongoClient(
                datbaseConnection.Value.ConnectionString);

            var mongoDatabase = mongoClient.GetDatabase(
                datbaseConnection.Value.DatabaseName);

            _Collection = mongoDatabase.GetCollection<FuelStationModel>(
                datbaseConnection.Value.CollectionName);
        }

        public async Task<FuelStationModel> Create(FuelModel request)
        {
 
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

                var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, request.StationsId);

                var multiUpdateDefinition = Builders<FuelStationModel>.Update
                    .Push(u => u.Fuel, fuelModel);

                var pushNotificationsResult = await _Collection.UpdateOneAsync(firstStationFilter, multiUpdateDefinition);


                //var results = collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();
                var results = _Collection.Find(i => i.Id == request.StationsId).ToList();

                return results[0];

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

            return null;

        }

        public async Task<List<FuelModel>> GetFuelByID(string id)
        {

            FuelModel fuel = new FuelModel();

            var res = await _FuelCollection.FindAsync(c => c.Id == id);
            return res.ToList();


        }

    }
}

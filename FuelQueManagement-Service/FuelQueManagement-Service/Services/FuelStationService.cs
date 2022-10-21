using FuelQueManagement_Service.Models;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using System;

namespace FuelQueManagement_Service.Services
{
    public class FuelStationService
    {
        //creating the database connection
        private readonly IMongoCollection<FuelStationModel> _Collection;
        public FuelStationService(IOptions<DatabaseConnection> datbaseConnection)
        {
            var mongoClient = new MongoClient(
                datbaseConnection.Value.ConnectionString);

            var mongoDatabase = mongoClient.GetDatabase(
                datbaseConnection.Value.DatabaseName);

            _Collection = mongoDatabase.GetCollection<FuelStationModel>(
                datbaseConnection.Value.CollectionName);
        }

        // This is required to create a fuel station
        public async Task<FuelStationModel> Create(FuelStationModel request)
        {
            FuelStationModel fuelStation = new FuelStationModel();
            fuelStation.Name = request.Name;
            fuelStation.Location = request.Location;
            fuelStation.StationOwner = request.StationOwner;
            fuelStation.LastModified = DateTime.Now;
            fuelStation.DieselStatus = false;
            fuelStation.PetrolStatus = false;
            fuelStation.Fuel = new FuelModel[0];
            fuelStation.Queue = new QueueModel[0];

            await _Collection.InsertOneAsync(fuelStation);
            var res = _Collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

            return res[0];
        }

        // This is required to get all fuel stations
        public async Task<List<FuelStationModel>> GetFuelStations()
        {
            var res = _Collection.Find(_ => true).ToList();
            return res;
        }

        // This is required to get fuel station by id
        public async Task<FuelStationModel> GetFuelStationById(string id)
        {
            var res = await _Collection.FindAsync(c => c.Id == id);
            return res.ToList()[0];
        }

        // This is required to update diesel status
        public async Task<FuelStationModel> UpdateDieselStatus(bool status, string id)
        {
            FuelStationModel fuelStation = new FuelStationModel();
            fuelStation.DieselStatus = status;

            var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, id);
            var updateDefinition = Builders<FuelStationModel>.Update
                .Set(u => u.DieselStatus, fuelStation.DieselStatus);
            var updatedResult = await _Collection
                .UpdateOneAsync(firstStationFilter,
                updateDefinition);
            var res = _Collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

            return res[0];
        }

        // This is required to update petrol status
        public async Task<FuelStationModel> UpdatePetrolStatus(bool status, string id)
        {
            FuelStationModel fuelStation = new FuelStationModel();
            fuelStation.PetrolStatus = status;

            var firstStationFilter = Builders<FuelStationModel>.Filter.Eq(a => a.Id, id);
            var updateNameDefinition = Builders<FuelStationModel>.Update
                .Set(u => u.PetrolStatus, fuelStation.PetrolStatus);
            var updateNameResult = await _Collection
                .UpdateOneAsync(firstStationFilter,
                updateNameDefinition);
            var res = _Collection.Find(_ => true).Limit(1).SortByDescending(i => i.Id).ToList();

            return res[0];
        }


    }
}

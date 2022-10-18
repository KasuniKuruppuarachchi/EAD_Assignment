using FuelQueManagement_Service.Models;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using System;

namespace FuelQueManagement_Service.Services
{
    public class FuelStationService
    {
        private readonly IMongoCollection<FuelStationModel> _Collection;
        public FuelStationService(IOptions<DatabaseConnection> datbaseConnection)
        {
            var mongoClient = new MongoClient("mongodb+srv://root:root@fuelqueue.qnpg99v.mongodb.net/FuelQueue?retryWrites=true&w=majority");

            var mongoDatabase = mongoClient.GetDatabase("FuelQueue");

            _Collection = mongoDatabase.GetCollection<FuelStationModel>("fuelstation");
        }

        public async Task<FuelStationModel> Create(FuelStationModel request)
        {

            try
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

                _Collection.InsertOneAsync(fuelStation);

                var res = _Collection   .Find(_ => true).Limit(1).SortBy(i => i.LastModified).ToList();

                return res[0];
            }
            catch
            {
                return null;
            }
        }

        public async Task<List<FuelStationModel>> GetFuelStations()
        {

            FuelStationModel fuelStation = new FuelStationModel();


            var res = _Collection.Find(_ => true).ToList();
            return res;
        }


    }
}

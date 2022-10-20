using FuelQueManagement_Service.Models;
using FuelQueManagement_Service.Services;
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
        // Declearing the fuel station service instance
        private readonly QueueService _queueService;
        public QueueController(QueueService queueService) =>
            _queueService = queueService;

        // This is required to create a queue object
        [HttpPost]
        public async Task<FuelStationModel> Create(QueueModel request)
        {
            try
            {
                var res = await _queueService.Create(request);
                return res;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        // This is required to delete a queue object
        [HttpDelete]
        public async Task<FuelStationModel> Delete(string fuelType, string stationId, string vehicleOwner)
        {
            try
            {
                var res = await _queueService.Delete(fuelType,stationId,vehicleOwner);
                return res;
            }
            catch (Exception e)
            {
                return null;
            }
        }

    }

}

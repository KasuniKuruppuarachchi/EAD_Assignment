using FuelQueManagement_Service.Models;
using FuelQueManagement_Service.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualBasic;
using MongoDB.Bson;
using MongoDB.Driver;
using MongoDB.Driver.Core.Operations;

namespace FuelQueManagement_Service.Controllers;

[ApiController]
[Route("[controller]")]
public class FuelController : ControllerBase
{
    //Declearing the fuel service instance
    private readonly FuelService _fuelService;
    public FuelController(FuelService fuelService) =>
        _fuelService = fuelService;

    //This is required to create a fuel object 
    [HttpPost]
    public async Task<FuelStationModel> Create(FuelModel request) 
    {
        try
        {
            var res = await _fuelService.Create(request);
            return res;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}

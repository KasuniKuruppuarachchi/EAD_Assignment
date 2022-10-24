using FuelQueManagement_Service.Models;
using FuelQueManagement_Service.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.Configure<DatabaseConnection>(
    builder.Configuration.GetSection("DatabaseConnection"));

// building the services
builder.Services.AddSingleton<FuelStationService>();
builder.Services.AddSingleton<FuelService>();
builder.Services.AddSingleton<QueueService>();

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();

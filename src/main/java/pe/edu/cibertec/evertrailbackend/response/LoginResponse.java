package pe.edu.cibertec.evertrailbackend.response;

public record LoginResponse(String token, String username, String rol, String mensaje, Long id) {
}

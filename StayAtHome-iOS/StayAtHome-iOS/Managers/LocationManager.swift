import CoreLocation
import Foundation

class LocationManager: NSObject, CLLocationManagerDelegate {
    
    typealias CompletionClosure = ((_ autorized: Bool,_ location: CLLocation?) -> Void)
    
    var locationManager: CLLocationManager?
    
    var didChangeAuthorizationCompletion: CompletionClosure?
    
    func askForLocationService(_ completion: @escaping CompletionClosure) {
        didChangeAuthorizationCompletion = completion
        locationManager = CLLocationManager()
        locationManager?.delegate = self
        locationManager?.requestWhenInUseAuthorization()
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if status == .authorizedWhenInUse || status == .authorizedAlways {
            if CLLocationManager.isMonitoringAvailable(for: CLBeaconRegion.self) {
                if CLLocationManager.isRangingAvailable() {
                    didChangeAuthorizationCompletion?(true, manager.location)
                    return
                }
            }
        }
        didChangeAuthorizationCompletion?(false, nil)
    }
    
}

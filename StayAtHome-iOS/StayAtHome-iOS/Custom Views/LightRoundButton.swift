
import UIKit

@IBDesignable
class LightRoundButton: UIButton {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }
    
    
    func setup() {
        
        let equalEdgeInsets: CGFloat = 16
        titleEdgeInsets = UIEdgeInsets(top: equalEdgeInsets, left: equalEdgeInsets, bottom: equalEdgeInsets, right: equalEdgeInsets)
        backgroundColor = UIColor(named: "azure")
        titleLabel?.font = UIFont(name: "FredokaOne-Regular", size: 30)
        layer.cornerRadius = 16
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setup()
    }
}

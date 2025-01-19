import "../css/popup.css"
import "../css/card.css"
import { useEffect } from "react";
function Popup({ isOpen, commentData, onClose }) {

    if (!isOpen) return null;
   
    return (
        <div className="popup-overlay">
            <div className="popup-container">
                <button className="popup-close" onClick={onClose}>&times;</button>
                <h3 className="popup-title">Comments</h3>
                <div className="popup-comments">
                    {(
                        commentData.map((data) => (<>
                            <div className="post-header">
                                <img src={data.imageName} alt="user" className="user-avatar" />&nbsp;&nbsp;
                                <span className="username"><b>{data.username}</b></span>&nbsp;&nbsp;
                            </div>
                            <div className="popup-comment">
                                <strong className="popup-comment-user">{data.username}</strong>
                                <strong className="popup-comment-text">{data.text}</strong>
                            </div>
                        </>
                        ))
                    )
                    }
                </div>
            </div>
        </div>
    )

}
export default Popup;
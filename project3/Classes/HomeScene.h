/*
 * HomeScene.h
 *
 *  Created on: Mar 1, 2013
 *      Author: john
 */

#ifndef HOMESCENE_H_
#define HOMESCENE_H_

#include "cocos2d.h"

class HomeScene : public cocos2d::CCLayer
//class HomeScene : public cocos2d::CCLayer
{
public:
	HomeScene();
	virtual ~HomeScene();

    //Here's a difference. Method 'init' in cocos2d-x returns bool, instead of returning 'id' in cocos2d-iphone
    virtual bool init();

    //scene pointer
    static cocos2d::CCScene* scene();

    //implement the "static node()" method manually
    CREATE_FUNC(HomeScene);

    //menu callbacks
    void menuOptionCallback(CCObject* pSender);
    void menuBeginCallback(CCObject* pSender);
};

#endif /* HOMESCENE_H_ */
